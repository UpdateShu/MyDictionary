package com.geekbrains.mydictionary.mvp.model.data.retrofit

import com.geekbrains.mydictionary.mvp.model.api.DictionaryApi
import com.geekbrains.mydictionary.mvp.model.data.DataSourceInterface
import com.geekbrains.mydictionary.mvp.model.entities.SearchDTOItem
import com.geekbrains.mydictionary.mvp.model.entities.Word
import io.reactivex.rxjava3.core.Observable

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"

class RetrofitDataSource : DataSourceInterface<List<Word>> {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DictionaryApi::class.java)

    override fun getData(word: String): Observable<List<Word>> {
        return api.search(word)
            .map { convert(it) }
    }

    private fun convert(listData: List<SearchDTOItem>): List<Word> {
        val list: MutableList<Word> = mutableListOf()
        listData.forEach {
            val word = Word(
                word = it.text,
                meanings = convertMeanings(it.meanings)
            )
            list.add(word)
        }
        return list
    }

    private fun convertMeanings(meaningsData: List<SearchDTOItem.Meaning>): List<Word.Meanings> {
        val listMeaning : MutableList<Word.Meanings> = mutableListOf()
        meaningsData.forEach {
            val meanings = Word.Meanings(
                imageUrl = it.imageUrl,
                translation = convertTranslation(it.translation)
            )
            listMeaning.add(meanings)
        }
        return listMeaning
    }

    private fun convertTranslation(translation: SearchDTOItem.Translation)
        = Word.Translation(text = translation.text)
}