package com.geekbrains.mydictionary.mvvm.model.repo.datasource.retrofit

import com.geekbrains.mydictionary.mvvm.model.entities.SearchDTOItem

import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("words/search")
    suspend fun search(@Query("search") wordToSearch: String)
        : List<SearchDTOItem>
}