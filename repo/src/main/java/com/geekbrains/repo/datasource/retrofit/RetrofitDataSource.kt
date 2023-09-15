package com.geekbrains.repo.datasource.retrofit

import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.DataSourceInterface
import com.geekbrains.repo.datasource.convertSearchDtoToWords
import com.geekbrains.utils.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitDataSource : DataSourceInterface<List<Word>> {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DictionaryApi::class.java)

    override suspend fun getDataBySearchWord(word: String)
        = convertSearchDtoToWords(api.search(word))

    override suspend fun getData(): List<Word> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(idWord: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun setDataLocal(words: List<Word>) {
        TODO("Not yet implemented")
    }
}