package com.osman.bitirmeprojesi.di

import com.google.gson.annotations.Since
import com.osman.bitirmeprojesi.data.DataSource
import com.osman.bitirmeprojesi.repo.Repository
import com.osman.bitirmeprojesi.retrofit.ApiUtils
import com.osman.bitirmeprojesi.retrofit.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule  {
    @Provides
    @Singleton
    fun provideTasksRepository(dataSource:DataSource):Repository{
        return Repository(dataSource)
    }

    @Provides
    @Singleton
    fun provideDataSource(foodDao: FoodDao):DataSource{
        return DataSource(foodDao)
    }

    @Provides
    @Singleton
    fun provideFoodDao() : FoodDao{
        return ApiUtils.getFoodDao()
    }


}