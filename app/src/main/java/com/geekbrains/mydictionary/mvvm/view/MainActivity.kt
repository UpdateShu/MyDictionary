package com.geekbrains.mydictionary.mvvm.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainBinding
import com.geekbrains.mydictionary.mvvm.model.entities.AppState
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.viewmodel.MainViewModel

import com.geekbrains.mydictionary.utils.MAIN_VIEWMODEL
import com.geekbrains.mydictionary.utils.RELOAD_LOCAL
import com.geekbrains.mydictionary.utils.RELOAD_ONLINE
import com.geekbrains.mydictionary.utils.isOnline

import com.google.android.material.snackbar.Snackbar

import org.koin.core.qualifier.named
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ViewInterface {
    interface OnClickWord {
        fun onClickWord(word: Word)
    }

    private var searchWord: String? = null
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel(named(MAIN_VIEWMODEL))

    private val adapter = MainRvAdapter(object : OnClickWord {
        override fun onClickWord(word: Word) {
            showError(word.word, false)
        }
    })

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
                viewModel.getDataViewModel(searchWord!!, binding.root.isOnline(this@MainActivity))
                    .observe(this, Observer { state ->
                        rangeData(state)
                    })
            }
        }
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
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}