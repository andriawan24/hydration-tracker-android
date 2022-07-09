package com.andriawan.hydrationtracker.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.theme.OnPrimaryColor
import com.andriawan.hydrationtracker.utils.navigation.Routes

@Composable
fun AppBottomNav(
    navController: NavHostController
) {
    val bottomNavList = listOf(
        Routes.HistoryPage,
        Routes.HomePage,
        Routes.SettingsPage
    )

    val navBackstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackstackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 0.dp
    ) {
        bottomNavList.map {
            BottomNavigationItem(
                selected = it.routeName == currentRoute,
                onClick = {
                    navController.navigate(it.routeName) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = OnPrimaryColor
            )
        }
    }
}

@Preview
@Composable
fun AppBottomNavPreview() {
    HydrationTrackerTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            AppBottomNav(
                navController = rememberNavController()
            )
        }
    }
}