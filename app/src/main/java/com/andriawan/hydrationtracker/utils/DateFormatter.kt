package com.andriawan.hydrationtracker.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    fun formatDate(date: Date): Date? {
        return try {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val resultString = simpleDateFormat.format(date)

            simpleDateFormat.parse(resultString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}