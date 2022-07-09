package com.andriawan.hydrationtracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class DailyHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val date: Date,
    val totalAmount: Int = 0
)