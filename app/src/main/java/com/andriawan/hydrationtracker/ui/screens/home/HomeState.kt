package com.andriawan.hydrationtracker.ui.screens.home

import com.andriawan.hydrationtracker.data.models.DailyHistory
import com.andriawan.hydrationtracker.data.models.DrinkType

data class HomeState(
    val history: DailyHistory? = null,
    val drinkTypes: List<DrinkType>? = null,
    val percentage: Float = 0F,
    val totalAmount: Int = 0
)