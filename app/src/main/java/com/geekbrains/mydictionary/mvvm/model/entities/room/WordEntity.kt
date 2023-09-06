package com.geekbrains.mydictionary.mvvm.model.entities.room

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WordEntity(
    @PrimaryKey
    val wId: String,

    @Embedded
    val meanings: Meaning,

    @ColumnInfo(name = "text")
    val text: String)
{
    data class Meaning(
        val mId: Int,
        val imageUrl: String?,

        @Embedded
        val translation: Translation)
    {
        class Translation(
            val translationText: String?
        )
    }
}