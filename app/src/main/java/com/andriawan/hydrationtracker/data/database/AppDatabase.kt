package com.andriawan.hydrationtracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andriawan.hydrationtracker.data.database.dao.DailyDrinkDAO
import com.andriawan.hydrationtracker.data.models.DailyHistory

@Database(entities = [DailyHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dailyDrinkDao(): DailyDrinkDAO

    companion object {
        const val DATABASE_NAME = "HydrationTrackerDatabase"
    }
}
