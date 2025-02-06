package com.andriawan.hydrationtracker.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andriawan.hydrationtracker.ui.screens.history.HistoryScreen
import com.andriawan.hydrationtracker.ui.screens.home.HomeScreen
import com.andriawan.hydrationtracker.ui.screens.settings.SettingsScreen
import com.andriawan.hydrationtracker.utils.navigation.Routes

@ExperimentalMaterialApi
@Composable
fun AppNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.HomePage.routeName
    ) {
        composable(Routes.HomePage.routeName) {
            HomeScreen()
        }

        composable(Routes.SettingsPage.routeName) {
            SettingsScreen()
        }

        composable(Routes.HistoryPage.routeName) {
            HistoryScreen()
        }
    }
}
