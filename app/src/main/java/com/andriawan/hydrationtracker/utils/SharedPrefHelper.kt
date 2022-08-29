package com.andriawan.hydrationtracker.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefHelper {

    private var sharedPreferences: SharedPreferences? = null

    private const val PREF_NAME = "hydration-preferences"
    const val PREF_DAILY_GOAL = "daily_goal"
    const val DEFAULT_DAILY_GOAL = 2700

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        sharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun readString(key: String, defaultValue: String): String {
        return sharedPreferences?.getString(key, defaultValue) ?: ""
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences?.edit()?.putInt(key, value)?.apply()
    }

    fun readInt(key: String, defaultValue: Int): Int {
        return sharedPreferences?.getInt(key, defaultValue) ?: 0
    }
}