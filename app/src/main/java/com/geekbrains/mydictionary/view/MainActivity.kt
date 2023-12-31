package com.geekbrains.mydictionary.view

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainBinding
import com.geekbrains.mydictionary.view.favorite.FavoriteFragment

import com.geekbrains.utils.RELOAD_LOCAL
import com.geekbrains.utils.RELOAD_ONLINE
import com.geekbrains.utils.viewById
import com.geekbrains.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity

private const val SLIDE_LEFT_DURATION = 1000L
private const val COUNTDOWN_DURATION = 2000L
private const val COUNTDOWN_INTERVAL = 1000L

class MainActivity : ScopeActivity() {
    interface OnClickWord {
        fun onClickWord(word: Word)
        fun onClickToFavorite(word: Word, favoriteState: Boolean)
    }

    private var flag: Boolean = false
    private var searchWord: String? = null
    private lateinit var binding: ActivityMainBinding

    private var isOnline: Boolean = true

    private lateinit var viewModel: MainViewModel

    private val favoriteFAB by viewById<FloatingActionButton>(R.id.fabFavorite)

    private val adapter : MainRvAdapter by lazy {
        MainRvAdapter(object : OnClickWord {
            override fun onClickWord(word: Word) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        word.word,
                        word.meanings?.translation?.text ?: "",
                        word.meanings?.imageUrl ?: ""
                    )
                )

                showError(word.word, false)
            }

            override fun onClickToFavorite(word: Word, favoriteState: Boolean) {
                word.isFavorite = favoriteState
                viewModel.setFavorite(word)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.rvMainDictionary
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
            LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        binding.tilSearchWord.setEndIconOnClickListener{
            hideKeyBoard()

            searchWord = binding.etSearchWord.text?.toString()
            if (!searchWord.isNullOrEmpty()) {
                viewModel.getDataViewModel(searchWord!!)
                    .observe(this, Observer { state ->
                        rangeData(state)
                    })
            }
        }
        favoriteFAB.setOnClickListener {
            flag = !flag
            if (flag) {
                binding.llContainer.visibility = View.GONE
                binding.fcvContainer.visibility = View.VISIBLE

                favoriteFAB.setImageResource(R.drawable.ic_baseline_favorite_24)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fcvContainer, FavoriteFragment.newInstance())
                    .commit()
            } else {
                binding.llContainer.visibility = View.VISIBLE
                binding.fcvContainer.visibility = View.GONE

                favoriteFAB.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
        val model: MainViewModel by inject()
        viewModel = model
        viewModel.initNetworkValidation(this@MainActivity)
            .observe(this, Observer { state -> rangeData(state) })
        setDefaultSplashScreen()
    }

    fun rangeData(state: AppState) {
        when (state) {
            is AppState.Success -> {
                val receivedData = state.data
                if (receivedData.isNotEmpty()) {
                    showData(receivedData)
                } else {
                    showError(
                        resources.getString(R.string.empty_server_response_on_success), true, isOnline = false
                    )
                }
            }
            is AppState.Loading -> {
                binding.pbSearch.isVisible = state.progress == null
            }
            is AppState.Error -> {
                showError(state.error, true, isOnline = true)
            }
            is AppState.OnlineChanged -> {
                isOnline = state.isOnline
                showError(if (isOnline) "Online" else "Offline", true, isOnline)
                Log.v("MyDictionary", isOnline.toString())
            }
        }
    }

    private fun showData(data: List<Word>) {
        adapter.setDataInRv(data)
    }

    private fun showError(message: String, isAction: Boolean, isOnline: Boolean = true) {
        val sb = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        if (isAction) {
            if (isOnline) {
                sb.setAction(RELOAD_ONLINE) {
                    if (!searchWord.isNullOrEmpty()) {
                        viewModel.getDataViewModel(searchWord!!)
                    } else {
                        showError(resources.getString(R.string.error_empty), false)
                    }
                }
            } else {
                sb.setAction(RELOAD_LOCAL) {
                    if (!searchWord.isNullOrEmpty()) {
                        viewModel.getDataViewModel(searchWord!!)
                    } else {
                        showError(resources.getString(R.string.error_empty), false)
                    }
                }
            }
        }
        sb.show()
    }

    private fun hideKeyBoard() {
        this.currentFocus?.let {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun setDefaultSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
            setSplashScreenDuration()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener {splashScreenView ->
            val slideDown = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                -splashScreenView.width.toFloat(),
                0f
            )

            slideDown.interpolator = AnticipateInterpolator()
            slideDown.duration = SLIDE_LEFT_DURATION

            slideDown.doOnEnd{
                splashScreenView.remove()
            }
            slideDown.start()
        }
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            override fun onTick(p0: Long) {
                // Nothing to do
            }

            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }
}