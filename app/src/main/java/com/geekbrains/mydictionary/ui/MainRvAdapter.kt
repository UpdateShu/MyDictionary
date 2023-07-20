package com.geekbrains.mydictionary.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainRvItemBinding
import com.geekbrains.mydictionary.mvp.model.entities.Word

class MainRvAdapter(val onClick: MainActivity.OnClickWord)
    : RecyclerView.Adapter<MainRvAdapter.MainRvViewHolder>() {

    inner class MainRvViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        fun bind(word: Word) {
            ActivityMainRvItemBinding.bind(itemView).apply {
                tvHeaderItem.text = word.word
                tvDescriptionItem.text = word.meanings.first().translation?.text
                llContainerItem.setOnClickListener {
                    onClick.onClickWord(word)
                }
            }
        }
    }

    private val listData: MutableList<Word> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataInRv(data: List<Word>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MainRvViewHolder {
        return MainRvViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_rv_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainRvViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}