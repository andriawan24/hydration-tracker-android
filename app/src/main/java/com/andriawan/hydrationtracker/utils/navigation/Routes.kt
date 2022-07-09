package com.andriawan.hydrationtracker.utils.navigation

import androidx.annotation.DrawableRes
import com.andriawan.hydrationtracker.R

sealed class Routes(
    val routeName: String,
    @DrawableRes val icon: Int
) {
    object HomePage: Routes(routeName = "home_page", icon = R.drawable.ic_water_drop)
    object HistoryPage: Routes(routeName = "history_page", icon = R.drawable.ic_history)
    object SettingsPage: Routes(routeName = "settings_page", icon = R.drawable.ic_settings_filled)
}
