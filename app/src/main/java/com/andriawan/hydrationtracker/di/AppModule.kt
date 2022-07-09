package com.andriawan.hydrationtracker.di

import androidx.work.WorkerFactory
import com.andriawan.hydrationtracker.data.repository.DailyHistoryRepository
import com.andriawan.hydrationtracker.utils.CustomWorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCustomWorkerFactory(
        repository: DailyHistoryRepository
    ): WorkerFactory {
        return CustomWorkerFactory(repository)
    }
}