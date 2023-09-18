package com.geekbrains.mydictionary.view

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.geekbrains.entities.Word

class WordsAdapterDelegate<VH : ViewHolder> (val rv : RecyclerView.Adapter<VH>, val words: MutableList<Word>)
{
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Word>) {
        words.clear()
        words.addAll(data)
        rv.notifyDataSetChanged()
    }
}

fun <VH : ViewHolder> RecyclerView.Adapter<VH>.setWords(words: MutableList<Word>, data: List<Word>) {
    val wordsAdapterDelegate = WordsAdapterDelegate(this, words)
    wordsAdapterDelegate.setData(data)
}