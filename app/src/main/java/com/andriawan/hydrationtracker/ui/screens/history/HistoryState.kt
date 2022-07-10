package com.andriawan.hydrationtracker.ui.screens.history

import com.andriawan.hydrationtracker.data.models.DailyHistory

data class HistoryState(
    val histories: List<DailyHistory>? = null
)