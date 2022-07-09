package com.andriawan.hydrationtracker.data.models

import androidx.annotation.DrawableRes

data class DrinkType(
    val name: String,
    @DrawableRes val icon: Int,
    val amount: Int
)