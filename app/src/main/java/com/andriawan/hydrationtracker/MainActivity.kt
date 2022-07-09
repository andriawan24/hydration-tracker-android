package com.andriawan.hydrationtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.andriawan.hydrationtracker.navigation.AppNavGraph
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.ui.components.AppBottomNav
import com.andriawan.hydrationtracker.utils.WorkerHelper
import com.andriawan.hydrationtracker.worker.HistoryAddWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Installing Splash Screen
        installSplashScreen()

        setContent {
            HydrationTrackerTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                            elevation = 0.dp
                        ) {
                            AppBottomNav(
                                navController = navController
                            )
                        }
                    },
                    content = {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            AppNavGraph(navController = navController)
                        }
                    }
                )
            }
        }

        lifecycleScope.launch {
            initDatabaseWorker()
        }
    }

    /**
     * Create a function for scheduling create database every night
     * at 00:00:00 am
     */
    private fun initDatabaseWorker() {
        if (WorkManager.getInstance(this)
                .getWorkInfosForUniqueWork(HistoryAddWorker.UNIQUE_WORKER_NAME).get().isEmpty()
        ) {
            WorkerHelper.createHistoryDrinkWorker(
                context = applicationContext,
                scheduleType = WorkerHelper.ScheduleType.NOW
            )
        }
    }
}