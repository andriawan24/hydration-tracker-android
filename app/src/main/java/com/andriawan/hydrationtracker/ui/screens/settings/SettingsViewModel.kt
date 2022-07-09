package com.andriawan.hydrationtracker.ui.screens.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.andriawan.hydrationtracker.utils.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(SettingsState())
    private set

    init {
        initData()
    }

    private fun initData() {
        val dailyGoals = SharedPrefHelper.readInt(SharedPrefHelper.PREF_DAILY_GOAL, 0)
        state = state.copy(
            dailyGoals = dailyGoals
        )
    }

    fun saveNewGoals(newGoals: Int) {
        SharedPrefHelper.saveInt(SharedPrefHelper.PREF_DAILY_GOAL, newGoals)
        initData()
    }
}