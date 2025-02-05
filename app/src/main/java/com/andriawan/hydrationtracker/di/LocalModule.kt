package com.andriawan.hydrationtracker.di

import android.content.Context
import androidx.room.Room
import com.andriawan.hydrationtracker.data.database.AppDatabase
import com.andriawan.hydrationtracker.data.database.dao.DailyDrinkDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesDailyHistoryDao(appDatabase: AppDatabase): DailyDrinkDAO {
        return appDatabase.dailyDrinkDao()
    }
}
