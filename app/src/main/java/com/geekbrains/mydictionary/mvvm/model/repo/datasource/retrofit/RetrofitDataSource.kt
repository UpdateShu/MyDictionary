package com.geekbrains.mydictionary.mvvm.model.repo.datasource.retrofit

import com.geekbrains.mydictionary.mvvm.model.entities.SearchDTOItem
import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.repo.datasource.DataSourceInterface

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

    override suspend fun getDataBySearchWord(word: String): List<Word> {
        val searchList = api.search(word)
        val result: MutableList<Word> = mutableListOf()
        searchList.forEach {
            val word = Word(
                id = it.id.toString(),
                word = it.text,
                meanings = convertMeanings(it.meanings)
            )
            result.add(word)
        }
        return result
    }

    override suspend fun getData(): List<Word> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(idWord: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun setDataLocal(words: List<Word>) {
        TODO("Not yet implemented")
    }

    private fun convert(listData: List<SearchDTOItem>): List<Word> {
        val list: MutableList<Word> = mutableListOf()
        listData.forEach {
            val word = Word(
                id = it.id.toString(),
                word = it.text,
                meanings = convertMeanings(it.meanings)
            )
            list.add(word)
        }
        return list
    }

    private fun convertMeanings(meaningsData: List<SearchDTOItem.Meaning>): Word.Meanings {
        val listMeaning : MutableList<Word.Meanings> = mutableListOf()
        meaningsData.forEach {
            val meanings = Word.Meanings(
                imageUrl = "https:" + it.imageUrl,
                translation = convertTranslation(it.translation)
            )
            listMeaning.add(meanings)
        }
        return listMeaning[0]
    }

    private fun convertTranslation(translation: SearchDTOItem.Translation)
        = Word.Meanings.Translation(text = translation.text)
}