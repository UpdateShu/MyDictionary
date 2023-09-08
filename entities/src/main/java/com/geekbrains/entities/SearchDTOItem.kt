package com.geekbrains.entities

import com.google.gson.annotations.SerializedName

class SearchDTOItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("meanings")
    val meanings: List<Meaning>,
    @SerializedName("text")
    val text: String
) {
    inner class Meaning(
        @SerializedName("id")
        val id: Int,
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("partOfSpeechCode")
        val partOfSpeechCode: String,
        @SerializedName("previewUrl")
        val previewUrl: String,
        @SerializedName("soundUrl")
        val soundUrl: String,
        @SerializedName("transcription")
        val transcription: String,
        @SerializedName("translation")
        val translation: Translation
    )

    class Translation(
        @SerializedName("note")
        val note: Any,
        @SerializedName("text")
        val text: String
    )
}