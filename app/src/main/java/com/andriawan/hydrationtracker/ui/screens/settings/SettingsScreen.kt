package com.andriawan.hydrationtracker.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.ui.components.DialogEditValue
import com.andriawan.hydrationtracker.ui.components.Setting
import com.andriawan.hydrationtracker.ui.components.SettingHeader

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SettingsPageTopBar()
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SettingHeader(title = stringResource(id = R.string.application_settings))
            Column {
                Setting(
                    title = stringResource(id = R.string.settings_goal_amount),
                    value = state.dailyGoals.toString(),
                    onItemClicked = {
                        showDialog = !showDialog
                    }
                )
            }
        }
    }

    if (showDialog) {
        DialogEditValue(
            title = stringResource(id = R.string.settings_edit_goal_amount),
            value = state.dailyGoals.toString(),
            onSubmit = {
                val newValue = it.toInt()
                viewModel.saveNewGoals(newValue)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
fun SettingsPageTopBar() {
    Text(
        text = stringResource(id = R.string.settings),
        style = MaterialTheme.typography.h1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun SettingsScreenPreview() {
    HydrationTrackerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SettingsScreen()
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreviewDarkMode() {
    HydrationTrackerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            SettingsScreen()
        }
    }
}

