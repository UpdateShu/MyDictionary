package com.geekbrains.repo.datasource.room

import com.geekbrains.entities.Word
import com.geekbrains.entities.room.FavoriteWordEntity
import com.geekbrains.entities.room.WordDao

import com.geekbrains.repo.datasource.DataSourceInterface

class RoomFavoriteDataSource(private val dao: WordDao) : DataSourceInterface<List<Word>> {

    override suspend fun getDataBySearchWord(word: String): List<Word> {
        return dao.findFavoriteWord(word).map { favoriteWordEntity ->
            Word(favoriteWordEntity.fId.toString(),
                favoriteWordEntity.fWord,
                Word.Meanings(
                    imageUrl = favoriteWordEntity.fImageUrl ?: "",
                    translation = Word.Meanings.Translation(favoriteWordEntity.fTransient ?: "")
                ))
        }
    }

    override suspend fun setDataLocal(words: List<Word>) {
        val word = words.first()
        val favoriteEntity = FavoriteWordEntity(
            fId = word.id.toInt(),
            fWord = word.word,
            fTransient = word.meanings?.translation?.text,
            fImageUrl = word.meanings?.imageUrl
        )
        if (word.isFavorite) {
            dao.addToFavorite(favoriteEntity)
        } else {
            dao.deleteFavorite(favoriteEntity)
        }
    }

    override suspend fun getData(): List<Word> {
        return dao.getAllFavorite().map { favoriteWordEntity ->
            Word(favoriteWordEntity.fId.toString(),
                favoriteWordEntity.fWord,
                Word.Meanings(
                    imageUrl = favoriteWordEntity.fImageUrl ?: "",
                    translation = Word.Meanings.Translation(favoriteWordEntity.fTransient ?: "")
                ))
        }
    }

    override suspend fun deleteData(idWord: Int) {
        val word = dao.getWord(idWord)
        dao.deleteFavorite(word)
    }
}