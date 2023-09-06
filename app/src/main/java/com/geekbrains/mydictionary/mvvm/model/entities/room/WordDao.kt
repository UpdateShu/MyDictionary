package com.geekbrains.mydictionary.mvvm.model.entities.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cashWord(vararg word: WordEntity)

    @Query("SELECT * FROM WordEntity")
    suspend fun getAllWord(): List<WordEntity>

    @Query("SELECT * FROM WordEntity WHERE text LIKE '%' || :word || '%'")
    suspend fun findWord(word: String): List<WordEntity>

    @Query("SELECT * FROM FavoriteWordEntity WHERE fWord LIKE '%' || :word || '%'")
    suspend fun findFavoriteWord(word: String): List<FavoriteWordEntity>

    @Query("SELECT * FROM FavoriteWordEntity")
    suspend fun getAllFavorite(): List<FavoriteWordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(vararg word: FavoriteWordEntity)

    @Delete
    suspend fun deleteFavorite(word: FavoriteWordEntity)

    @Query("SELECT * FROM FavoriteWordEntity WHERE fId = :idWord")
    suspend fun getWord(idWord: Int): FavoriteWordEntity
}