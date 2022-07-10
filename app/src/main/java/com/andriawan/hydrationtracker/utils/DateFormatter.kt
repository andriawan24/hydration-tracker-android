package com.andriawan.hydrationtracker.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeParseException
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

    fun formatDateToString(date: Date): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val resultString = simpleDateFormat.format(date)

            resultString
        } catch (e: Exception) {
            Log.e(DateFormatter::class.simpleName, "formatDateToString: ${e.localizedMessage}")
            "Invalid date format!"
        }
    }
}