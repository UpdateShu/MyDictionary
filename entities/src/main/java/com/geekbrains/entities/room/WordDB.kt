package com.geekbrains.entities.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WordEntity::class, FavoriteWordEntity::class),
    version = 1, exportSchema = false)
abstract class WordDB : RoomDatabase()
{
    abstract fun getDao(): WordDao
}