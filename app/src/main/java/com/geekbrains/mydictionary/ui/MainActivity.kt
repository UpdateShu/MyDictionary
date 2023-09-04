package com.geekbrains.mydictionary.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainBinding

import com.geekbrains.mydictionary.mvp.model.entities.AppState
import com.geekbrains.mydictionary.mvp.model.entities.Word

import com.geekbrains.mydictionary.mvp.presenter.MainPresenter
import com.geekbrains.mydictionary.mvp.presenter.PresenterInterface
import com.geekbrains.mydictionary.mvp.view.ViewInterface

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ViewInterface {
    interface OnClickWord {
        fun onClickWord(word: Word)
    }

    private var searchWord: String? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: PresenterInterface

    private val adapter = MainRvAdapter(object : OnClickWord {
        override fun onClickWord(word: Word) {
            showError(word.word, false)
        }
    })

    override fun onStart() {
        presenter.onAttach(this)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter()

        val recyclerView = binding.rvMainDictionary
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,
            LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        binding.tilSearchWord.setEndIconOnClickListener{
            hideKeyBoard()

            searchWord = binding.etSearchWord.text?.toString()
            if (!searchWord.isNullOrEmpty()) {
                presenter.getDataPresenter(searchWord!!,
                    binding.root.isOnline(this@MainActivity))
            }
        }
    }

    override fun onStop() {
        presenter.onDetach(this)
        super.onStop()
    }

    override fun rangeData(state: AppState) {
        when (state) {
            is AppState.Error -> {
                showError(state.error.message.toString(), true, isOnline = true)
            }
            is AppState.Loading -> {
                binding.pbSearch.isVisible = state.progress == null
            }
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
                        presenter.getDataPresenter(searchWord!!, true)
                    } else {
                        showError(resources.getString(R.string.error_empty), false)
                    }
                }
            } else {
                sb.setAction("Reload Local") {
                    if (!searchWord.isNullOrEmpty()) {
                        presenter.getDataPresenter(searchWord!!, false)
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