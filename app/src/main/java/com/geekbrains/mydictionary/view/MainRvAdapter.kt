package com.geekbrains.mydictionary.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.geekbrains.entities.Word

import com.geekbrains.mydictionary.R
import com.geekbrains.mydictionary.databinding.ActivityMainRvItemBinding

class MainRvAdapter(val onClick: MainActivity.OnClickWord) :
    RecyclerView.Adapter<MainRvAdapter.MainRvViewHolder>() {

    private val listData: MutableList<Word> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setDataInRv(data: List<Word>) {
        setWords(listData, data)
    }

    inner class MainRvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            ActivityMainRvItemBinding.bind(itemView).apply {
                tvHeaderItem.text = word.word
                tvDescriptionItem.text = word.meanings?.translation?.text
                word.meanings?.imageUrl?.let {
                    Glide.with(itemView.context)
                        .load(it)
                        .centerCrop()
                        .transform(MultiTransformation(CircleCrop(), FitCenter()))
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .into(ivImageWord)
                }
                if (word.isFavorite){
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                llContainerItem.setOnClickListener {
                    onClick.onClickWord(word)
                }
                ivFavorite.setOnClickListener {
                    if(word.isFavorite){
                        ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }
                    onClick.onClickToFavorite(word, !word.isFavorite)
                    notifyItemChanged(layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRvViewHolder {
        return MainRvViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_rv_item, parent, false)
                    as View
        )
    }

    override fun onBindViewHolder(holder: MainRvViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}