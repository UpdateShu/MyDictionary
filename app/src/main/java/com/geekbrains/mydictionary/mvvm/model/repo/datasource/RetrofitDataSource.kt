package com.geekbrains.mydictionary.mvvm.model.repo.datasource

import com.geekbrains.mydictionary.mvvm.model.api.DictionaryApi
import com.geekbrains.mydictionary.mvvm.model.entities.SearchDTOItem
import com.geekbrains.mydictionary.mvvm.model.entities.Word

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

    private fun convertMeanings(meanings: List<SearchDTOItem.Meaning>): Word.Meanings {
        val result: MutableList<Word.Meanings> = mutableListOf()
        meanings.forEach {
            val meaning = Word.Meanings(
                imageUrl = "https:" + it.imageUrl,
                translation = convertTranslation(it.translation)
            )
            result.add(meaning)
        }
        return result[0]
    }

    private fun convertTranslation(translation: SearchDTOItem.Translation): Word.Meanings.Translation? {
        return Word.Meanings.Translation(translation.text)
    }

    override suspend fun setDataLocal(words: List<Word>) {
        TODO("Not yet implemented")
    }

    override suspend fun getData(): List<Word> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(idWord: Int) {
        TODO("Not yet implemented")
    }
}