package com.geekbrains.mydictionary.mvvm.model.entities.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteWordEntity(
    @PrimaryKey(autoGenerate = false)
    val fId : Int = 0,

    val fWord: String,

    val fTransient: String?,

    val fImageUrl: String?
)