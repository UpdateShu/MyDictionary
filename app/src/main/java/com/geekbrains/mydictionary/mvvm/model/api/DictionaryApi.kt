package com.geekbrains.mydictionary.mvvm.model.api

import com.geekbrains.mydictionary.mvvm.model.entities.SearchDTOItem
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String)
        : Observable<List<SearchDTOItem>>
}