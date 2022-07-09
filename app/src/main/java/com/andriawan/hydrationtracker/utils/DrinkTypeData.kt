package com.andriawan.hydrationtracker.utils

import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.data.models.DrinkType

object DrinkTypeData {

    fun getData(): List<DrinkType> {
        return listOf(
            DrinkType(
                name = "150ml",
                amount = 150,
                icon = R.drawable.ic_cups
            ),
            DrinkType(
                name = "250ml",
                amount = 250,
                icon = R.drawable.ic_bottle
            ),
            DrinkType(
                name = "330ml",
                amount = 330,
                icon = R.drawable.ic_glass
            )
        )
    }
}