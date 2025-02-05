package com.andriawan.hydrationtracker.utils.navigation

import androidx.annotation.DrawableRes
import com.andriawan.hydrationtracker.R

sealed class Routes(
    val routeName: String,
    @DrawableRes val icon: Int
) {
    data object HomePage: Routes(routeName = "home_page", icon = R.drawable.ic_water_drop)
    data object HistoryPage: Routes(routeName = "history_page", icon = R.drawable.ic_history)
    data object SettingsPage: Routes(routeName = "settings_page", icon = R.drawable.ic_settings_filled)
}
