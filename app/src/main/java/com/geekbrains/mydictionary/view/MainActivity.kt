package com.geekbrains.mydictionary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainBinding
import com.geekbrains.mydictionary.view.favorite.FavoriteFragment

import com.geekbrains.utils.MAIN_VIEWMODEL
import com.geekbrains.utils.RELOAD_LOCAL
import com.geekbrains.utils.RELOAD_ONLINE
import com.geekbrains.viewmodel.MainViewModel

import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.scope.ScopeActivity

import org.koin.core.qualifier.named
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {
    interface OnClickWord {
        fun onClickWord(word: Word)
        fun onClickToFavorite(word: Word, favoriteState: Boolean)
    }

    private var flag: Boolean = false
    private var searchWord: String? = null
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel(named(MAIN_VIEWMODEL))

    private val adapter : MainRvAdapter by lazy {
        MainRvAdapter(object : OnClickWord {
            override fun onClickWord(word: Word) {
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
        binding.fabFavorite.setOnClickListener {
            flag = !flag
            if (flag) {
                binding.llContainer.visibility = View.GONE
                binding.fcvContainer.visibility = View.VISIBLE
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fcvContainer, FavoriteFragment.newInstance())
                    .commit()
            } else {
                binding.llContainer.visibility = View.VISIBLE
                binding.fcvContainer.visibility = View.GONE
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
        viewModel.initNetworkValidation(this@MainActivity)
    }

    fun rangeData(state: AppState) {
        when (state) {
            is AppState.Success -> {
                val receivedData = state.data
                if (!receivedData.isNullOrEmpty()) {
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
                        viewModel.getDataViewModel(searchWord!!, true)
                    } else {
                        showError(resources.getString(R.string.error_empty), false)
                    }
                }
            } else {
                sb.setAction(RELOAD_LOCAL) {
                    if (!searchWord.isNullOrEmpty()) {
                        viewModel.getDataViewModel(searchWord!!, false)
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
}