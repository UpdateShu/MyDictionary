package com.geekbrains.repo.datasource.retrofit

import com.geekbrains.entities.SearchDTOItem
import com.geekbrains.entities.Word
import com.geekbrains.repo.datasource.DataSourceInterface
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitDataSource : DataSourceInterface<List<Word>> {

    private val api = Retrofit.Builder()
        .baseUrl(com.geekbrains.utils.BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
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

    private fun convertMeanings(meaningsData: List<SearchDTOItem.Meaning>) : Word.Meanings
    {
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