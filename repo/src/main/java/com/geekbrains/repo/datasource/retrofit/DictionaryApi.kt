package com.geekbrains.repo.datasource.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("words/search")
    suspend fun search(@Query("search") wordToSearch: String)
        : List<com.geekbrains.entities.SearchDTOItem>
}