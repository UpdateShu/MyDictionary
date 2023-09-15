package com.geekbrains.entities

import com.google.gson.annotations.SerializedName

class SearchDTOItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("meanings")
    val meanings: List<MeaningDTO>?,
    @SerializedName("text")
    val text: String?)
{
    inner class MeaningDTO(
        @SerializedName("id")
        val id: Int,
        @SerializedName("imageUrl")
        val imageUrl: String?,
        @SerializedName("partOfSpeechCode")
        val partOfSpeechCode: String?,
        @SerializedName("previewUrl")
        val previewUrl: String?,
        @SerializedName("soundUrl")
        val soundUrl: String?,
        @SerializedName("transcription")
        val transcription: String?,
        @SerializedName("translation")
        val translation: TranslationDTO?)

    class TranslationDTO(
        @SerializedName("note")
        val note: Any?,
        @SerializedName("text")
        val text: String?)
}