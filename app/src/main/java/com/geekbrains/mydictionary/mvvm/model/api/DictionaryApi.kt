package com.geekbrains.mydictionary.mvvm.model.api

import com.geekbrains.mydictionary.mvvm.model.entities.SearchDTOItem

import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("words/search")
    suspend fun searchAsync(@Query("search") wordToSearch: String)
        : List<SearchDTOItem>
}