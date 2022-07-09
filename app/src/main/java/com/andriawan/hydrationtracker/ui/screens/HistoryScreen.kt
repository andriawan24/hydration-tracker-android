package com.andriawan.hydrationtracker.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.utils.SharedPrefHelper

@Composable
fun HistoryScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello World history")
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    HydrationTrackerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HistoryScreen()
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HistoryScreenPreviewDarkMode() {
    HydrationTrackerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HistoryScreen()
        }
    }
}

