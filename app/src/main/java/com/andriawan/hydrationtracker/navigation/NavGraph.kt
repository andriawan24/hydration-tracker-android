package com.andriawan.hydrationtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andriawan.hydrationtracker.ui.screens.HistoryScreen
import com.andriawan.hydrationtracker.ui.screens.home.HomeScreen
import com.andriawan.hydrationtracker.ui.screens.SettingsScreen
import com.andriawan.hydrationtracker.utils.navigation.Routes

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    NavHost(
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