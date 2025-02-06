package com.andriawan.hydrationtracker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andriawan.hydrationtracker.data.models.DailyHistory

@Dao
interface DailyDrinkDAO {

    @Query("SELECT * FROM daily_histories WHERE date = :date")
    suspend fun getHistoryByDate(date: Long): DailyHistory

    @Query("SELECT * FROM daily_histories ORDER BY date DESC")
    suspend fun getAllHistories(): List<DailyHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdateDrinkHistory(drinkHistory: DailyHistory)
}
