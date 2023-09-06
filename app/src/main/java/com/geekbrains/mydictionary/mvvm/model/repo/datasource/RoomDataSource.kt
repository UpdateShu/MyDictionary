package com.geekbrains.mydictionary.mvvm.model.repo.datasource

import com.geekbrains.mydictionary.mvvm.model.entities.Word
import com.geekbrains.mydictionary.mvvm.model.entities.room.WordDao
import com.geekbrains.mydictionary.mvvm.model.entities.room.WordEntity

class RoomDataSource(private val dao: WordDao)
    : DataSourceInterface<List<Word>>
{
    override suspend fun getDataBySearchWord(word: String): List<Word> {
        val searchList = dao.findWord(word)
        val result: MutableList<Word> = mutableListOf()
        searchList.forEach {
            val word = Word(
                id = it.wId,
                word = it.text,
                meanings = convertMeanings(it.meanings)
            )
            result.add(word)
        }
        return result
    }

    private fun convertMeanings(meanings: WordEntity.Meaning): Word.Meanings {
        val result: MutableList<Word.Meanings> = mutableListOf()
        return Word.Meanings(
            imageUrl = "https:" + meanings.imageUrl,
            translation = convertTranslation(meanings.translation)
        )
    }

    private fun convertTranslation(translation: WordEntity.Meaning.Translation): Word.Meanings.Translation? {
        return Word.Meanings.Translation(translation.translationText)
    }

    override suspend fun setDataLocal(words: List<Word>) {
        words.forEach {
            val wordCash = WordEntity(
                wId = it.id,
                text = it.word,
                meanings = convertMeamings(it.meanings)
            )
            dao.cashWord(wordCash)
        }
    }

    private fun convertMeamings(meanings: Word.Meanings): WordEntity.Meaning {
        return WordEntity.Meaning(
            mId = 0,
            imageUrl = meanings.imageUrl,
            translation = WordEntity.Meaning.Translation(meanings.translation?.text)
        )
    }

    override suspend fun getData(): List<Word> {
        val list = dao.getAllWord()
        val result: MutableList<Word> = mutableListOf()
        list.forEach {
            val word = Word(
                id = it.wId,
                word = it.text,
                meanings = convertMeanings(it.meanings)
            )
            result.add(word)
        }
        return result
    }

    override suspend fun deleteData(idWord: Int) {
        dao.deleteFavorite(dao.getWord(idWord))
    }
}