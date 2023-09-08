package com.geekbrains.mydictionary.mvvm.model.entities.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class, FavoriteWordEntity::class], version = 1)
abstract class WordDB : RoomDatabase()
{
    abstract fun getDao(): WordDao
}