package com.geekbrains.mydictionary.view.favorite

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
import com.geekbrains.mydictionary.view.setWords

class FavoriteAdapter(private val onClickWord: FavoriteFragment.OnClickWord)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()
{
    private val list: MutableList<Word> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Word>) {
        setWords(list, data)
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word)
        {
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
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                llContainerItem.setOnClickListener {
                    onClickWord.onClickWord(word)
                }
                ivFavorite.setOnClickListener {
                    onClickWord.onClickToFavorite(word, !word.isFavorite)
                    list.remove(word)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_rv_item, parent, false)
                    as View
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}