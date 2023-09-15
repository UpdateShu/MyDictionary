package com.geekbrains.mydictionary.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.entities.AppState
import com.geekbrains.entities.Word
import com.geekbrains.mydictionary.databinding.FragmentFavoriteBinding
import com.geekbrains.viewmodel.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class FavoriteFragment : Fragment()
{
    interface OnClickWord {
        fun onClickWord(word: Word)
        fun onClickToFavorite(word: Word, favoriteState: Boolean)
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModel(named(com.geekbrains.utils.FAVORITE_VIEWMODEL))
    private val adapter: FavoriteAdapter = FavoriteAdapter(object : OnClickWord {
        override fun onClickWord(word: Word) {
            Snackbar.make(binding.root, word.word, Snackbar.LENGTH_LONG).show()
        }

        override fun onClickToFavorite(word: Word, favoriteState: Boolean) {
            viewModel.deleteIsFavorite(word.id.toInt())
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.rvFavorite
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        viewModel.getDataViewModel().observe(viewLifecycleOwner, Observer { state ->
            rangeData(state)
        })
        viewModel.getFavoritesList()
    }

    private fun rangeData(state: com.geekbrains.entities.AppState) {
        when (state) {
            is AppState.Error -> {
                Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.pbFavoriteProgress.visibility = if (state.progress != null){
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
            is AppState.Success -> {
                adapter.setData(state.data)
            }
        }
    }
}