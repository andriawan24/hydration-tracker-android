package com.andriawan.hydrationtracker.ui.screens.settings

import com.andriawan.hydrationtracker.utils.SharedPrefHelper

data class SettingsState(
    val dailyGoals: Int = SharedPrefHelper.DEFAULT_DAILY_GOAL
)