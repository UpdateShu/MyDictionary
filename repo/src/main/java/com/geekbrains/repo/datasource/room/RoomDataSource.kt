package com.geekbrains.repo.datasource.room

import com.geekbrains.entities.Word
import com.geekbrains.entities.room.WordDao
import com.geekbrains.entities.room.WordEntity

import com.geekbrains.repo.datasource.DataSourceInterface
import com.geekbrains.repo.datasource.convertWordEntityToWords

class RoomDataSource(private val dao: WordDao)
    : DataSourceInterface<List<Word>>
{
    override suspend fun getDataBySearchWord(word: String)
        = convertWordEntityToWords(dao.findWord(word))

    override suspend fun getData()
        = convertWordEntityToWords(dao.getAllWord())

    override suspend fun setDataLocal(words: List<Word>)
    {
        words.forEach {word ->
            val wordCash = WordEntity(
                wId = word.id,
                text = word.word,
                meanings = word.meanings?.let {
                    WordEntity.Meaning(
                        mId = 0,
                        imageUrl = it.imageUrl,
                        translation = WordEntity.Meaning.Translation(it.translation?.text))
                } ?: null
            )
            dao.cashWord(wordCash)
        }
    }

    override suspend fun deleteData(idWord: Int) {
        dao.deleteFavorite(dao.getWord(idWord))
    }
}