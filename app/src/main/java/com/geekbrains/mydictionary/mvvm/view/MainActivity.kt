package com.geekbrains.mydictionary.mvvm.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainBinding
import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.viewmodel.MainViewModel
import com.geekbrains.mydictionary.utils.isOnline

import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewInterface {
    interface OnClickWord {
        fun onClickWord(word: Word)
    }

    private var searchWord: String? = null
    private lateinit var binding: ActivityMainBinding

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MainViewModel

    private val adapter = MainRvAdapter(object : OnClickWord {
        override fun onClickWord(word: Word) {
            showError(word.word, false)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModelFactory.create(MainViewModel::class.java)
        binding.tilSearchWord.setEndIconOnClickListener{
            hideKeyBoard()

            searchWord = binding.etSearchWord.text?.toString()
            if (!searchWord.isNullOrEmpty()) {
                viewModel.getDataViewModel(searchWord!!, binding.root.isOnline(this@MainActivity))
                    .observe(this, Observer { state ->
                        rangeData(state)
                    })
            }
        }
        val recyclerView = binding.rvMainDictionary
        recyclerView.layoutManager = LinearLayoutManager(application)
        recyclerView.adapter = adapter
    }

    override fun rangeData(state: AppState) {
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
                showError(state.error.message.toString(), true, isOnline = true)
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
                sb.setAction("Reload Online") {
                    if (!searchWord.isNullOrEmpty()) {
                        viewModel.getDataViewModel(searchWord!!, true)
                    } else {
                        showError(resources.getString(R.string.error_empty), false)
                    }
                }
            } else {
                sb.setAction("Reload Local") {
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
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}