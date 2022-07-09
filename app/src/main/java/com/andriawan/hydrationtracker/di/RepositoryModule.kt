package com.andriawan.hydrationtracker.di

import com.andriawan.hydrationtracker.data.database.dao.DailyDrinkDAO
import com.andriawan.hydrationtracker.data.repository.DailyHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesDailyHistoryRepository(
        dailyDrinkDAO: DailyDrinkDAO
    ) : DailyHistoryRepository {
        return DailyHistoryRepository(dailyDrinkDAO)
    }
}