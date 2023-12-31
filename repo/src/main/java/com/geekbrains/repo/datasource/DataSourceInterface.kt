package com.geekbrains.repo.datasource

interface DataSourceInterface<T>
{
    suspend fun getDataBySearchWord(word:String): T
    suspend fun setDataLocal(words: T)
    suspend fun getData(): T
    suspend fun deleteData(idWord: Int)
}