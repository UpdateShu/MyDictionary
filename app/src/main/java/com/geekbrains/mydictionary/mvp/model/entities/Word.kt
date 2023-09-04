package com.geekbrains.mydictionary.mvp.model.entities

import com.google.gson.annotations.SerializedName

data class Word(
    @field:SerializedName("text") val word: String,
    @field:SerializedName("meanings") val meanings: List<Meanings>
) {
    data class Meanings(
        @field:SerializedName("translation") val translation: Translation?,
        @field:SerializedName("imageUrl") val imageUrl: String?
    )

    data class Translation(
        @field:SerializedName("text") val text: String?
    )
}