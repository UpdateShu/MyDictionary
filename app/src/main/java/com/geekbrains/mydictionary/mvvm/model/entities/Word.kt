package com.geekbrains.mydictionary.mvvm.model.entities

import com.google.gson.annotations.SerializedName

data class Word(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("text") val word: String,
    @field:SerializedName("meanings") val meanings: Meanings
) {
    data class Meanings(
        @field:SerializedName("translation") val translation: Translation?,
        @field:SerializedName("imageUrl") val imageUrl: String?
    )

    data class Translation(
        @field:SerializedName("text") val text: String?
    )
}