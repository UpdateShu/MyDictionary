package com.geekbrains.mydictionary.di

import com.geekbrains.mydictionary.mvvm.model.data.DataSourceInterface
import com.geekbrains.mydictionary.mvvm.model.data.Repository
import com.geekbrains.mydictionary.mvvm.model.data.RepositoryInterface
import com.geekbrains.mydictionary.mvvm.model.data.retrofit.RetrofitDataSource
import com.geekbrains.mydictionary.mvvm.model.data.room.RoomDataSource
import com.geekbrains.mydictionary.mvvm.model.entities.Word

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSourceInterface<List<Word>>)
        : RepositoryInterface<List<Word>> = Repository(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSourceInterface<List<Word>>)
        : RepositoryInterface<List<Word>> = Repository(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSourceInterface<List<Word>>
        = RetrofitDataSource()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSourceInterface<List<Word>>
        = RoomDataSource()

}