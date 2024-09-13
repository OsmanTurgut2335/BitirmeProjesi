package com.osman.bitirmeprojesi.di

import android.content.Context
import com.osman.bitirmeprojesi.data.DataSource
import com.osman.bitirmeprojesi.repo.Repository
import com.osman.bitirmeprojesi.retrofit.ApiUtils
import com.osman.bitirmeprojesi.retrofit.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

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
    fun provideDataSource(foodDao: FoodDao,context: Context):DataSource{
        return DataSource(foodDao,context)
    }

    @Provides
    @Singleton
    fun provideFoodDao() : FoodDao{
        return ApiUtils.getFoodDao()
    }
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

}