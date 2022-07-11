package com.andriawan.hydrationtracker.ui.screens.history

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.ui.components.HistoryItem
import com.andriawan.hydrationtracker.utils.DateFormatter

@Composable
fun HistoryScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, e ->
            when (e) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.initData()
                }

                else -> {}
            }
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        HistoryPageTopBar()

        state.histories?.let { histories ->
            LazyColumn {
                items(
                    items = histories,
                    key = {
                        it.id ?: 0
                    }
                ) {
                    HistoryItem(
                        date = DateFormatter.formatDateToString(it.date),
                        value = it.totalAmount.toString()
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryPageTopBar() {
    Text(
        text = "History",
        style = MaterialTheme.typography.h1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        textAlign = TextAlign.Center
    )
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

