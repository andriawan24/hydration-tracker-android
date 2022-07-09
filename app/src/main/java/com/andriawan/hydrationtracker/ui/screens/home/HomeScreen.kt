package com.andriawan.hydrationtracker.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.theme.SecondaryColor
import com.andriawan.hydrationtracker.ui.components.OptionCard
import com.andriawan.hydrationtracker.ui.components.OptionType
import com.andriawan.hydrationtracker.ui.components.PercentageProgress
import com.andriawan.hydrationtracker.ui.components.WavesAnimationBox
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val state = viewModel.state

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        WavesAnimationBox(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primaryVariant,
            progress = state.percentage
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Daily Balance",
                    style = MaterialTheme.typography.h1
                )

                Spacer(modifier = Modifier.height(18.dp))

                PercentageProgress(
                    value = (state.percentage * 100).toInt()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${state.history?.totalAmount ?: 0}ml out of ${state.totalAmount}ml",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                    snackbar = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SecondaryColor.copy(alpha = 0.6F)),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 18.dp, horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add_water),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(20.dp))

                                Text(
                                    text = it.message,
                                    modifier = Modifier
                                        .weight(2F)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.h3.copy(
                                        fontWeight = FontWeight.Medium
                                    )
                                )

                                it.actionLabel?.let { label ->
                                    Text(
                                        text = label,
                                        modifier = Modifier
                                            .clickable {
                                                it.performAction()
                                            }
                                            .weight(1F),
                                        textDecoration = TextDecoration.Underline,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    state.drinkTypes?.forEach { type ->
                        OptionCard(
                            onCardClicked = {
                                viewModel.addWater(type.amount)
                                scope.launch {
                                    snackBarHostState.currentSnackbarData?.dismiss()
                                    val snackbarResult = snackBarHostState.showSnackbar(
                                        message = "${type.name} Added",
                                        duration = SnackbarDuration.Short,
                                        actionLabel = "Undo"
                                    )

                                    when (snackbarResult) {
                                        SnackbarResult.ActionPerformed -> {
                                            viewModel.reduceWater(type.amount)
                                        }
                                        SnackbarResult.Dismissed -> {
                                            snackBarHostState.currentSnackbarData?.dismiss()
                                        }
                                    }
                                }
                            },
                            title = type.name,
                            icon = type.icon,
                            type = OptionType.COMMON_OPTION,
                            modifier = Modifier.weight(2F)
                        )
                    }

                    OptionCard(
                        onCardClicked = { },
                        title = "More",
                        icon = R.drawable.ic_more,
                        type = OptionType.MORE_OPTION,
                        modifier = Modifier.weight(2F)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HydrationTrackerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeScreen()
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDarkMode() {
    HydrationTrackerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeScreen()
        }
    }
}

