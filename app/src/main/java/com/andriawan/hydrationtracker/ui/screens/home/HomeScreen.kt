package com.andriawan.hydrationtracker.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.data.models.DrinkType
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.ui.components.*
import com.andriawan.hydrationtracker.utils.Constants.ANALYTICS_ADD_WATER
import com.andriawan.hydrationtracker.utils.Constants.ANALYTICS_OTHER_OPTION
import com.andriawan.hydrationtracker.utils.Constants.ANALYTICS_REDUCE_WATER
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    analytics: FirebaseAnalytics = Firebase.analytics
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state = viewModel.state
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, e ->
            when (e) {
                Lifecycle.Event.ON_RESUME -> {
                    viewModel.initDrinkType()
                    viewModel.initData()
                }

                else -> {}
            }
        }

        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(text = "Bottom Sheet 1")
            }
        },
        sheetShape = MaterialTheme.shapes.small.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        )
    ) {
        MainScreen(
            onOptionClicked = {
                viewModel.addWater(it.amount)
                scope.launch {
                    analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                        param(ANALYTICS_ADD_WATER, it.amount.toString())
                    }

                    snackBarHostState.currentSnackbarData?.dismiss()
                    val snackbarResult = snackBarHostState.showSnackbar(
                        message = context.getString(R.string.water_added, it.name),
                        duration = SnackbarDuration.Short,
                        actionLabel = context.getString(R.string.undo)
                    )

                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                                param(ANALYTICS_REDUCE_WATER, it.amount.toString())
                            }

                            viewModel.reduceWater(it.amount)
                        }
                        SnackbarResult.Dismissed -> {
                            snackBarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                }
            },
            onOtherOptionClicked = {
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                    param(ANALYTICS_OTHER_OPTION, Date().toString())
                }

                scope.launch {
                    bottomSheetState.show()
                }
            },
            state = state,
            snackBarHostState = snackBarHostState
        )
    }
}

@Composable
fun MainScreen(
    state: HomeState,
    snackBarHostState: SnackbarHostState,
    onOptionClicked: (DrinkType) -> Unit,
    onOtherOptionClicked: () -> Unit
) {
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
                    text = stringResource(id = R.string.daily_balance),
                    style = MaterialTheme.typography.h1
                )
                Spacer(modifier = Modifier.height(18.dp))
                PercentageProgress(value = (state.percentage * 100).toInt())
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        id = R.string.current_amount,
                        state.history?.totalAmount ?: 0,
                        state.totalAmount
                    ),
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                CustomSnackbar(snackbarHostState = snackBarHostState)
                OptionList(
                    drinkTypes = state.drinkTypes,
                    onOptionClicked = {
                        onOptionClicked(it)
                    },
                    onOtherOptionClicked = {
                        onOtherOptionClicked()
                    }
                )
            }
        }
    }
}

@Composable
private fun OptionList(
    drinkTypes: List<DrinkType>?,
    onOptionClicked: (DrinkType) -> Unit,
    onOtherOptionClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        drinkTypes?.forEach { type ->
            OptionCard(
                onCardClicked = {
                    onOptionClicked(type)
                },
                title = type.name,
                icon = type.icon,
                type = OptionType.COMMON_OPTION,
                modifier = Modifier.weight(2F)
            )
        }

        OptionCard(
            onCardClicked = { onOtherOptionClicked() },
            title = stringResource(id = R.string.more_option),
            icon = R.drawable.ic_more,
            type = OptionType.MORE_OPTION,
            modifier = Modifier.weight(2F)
        )
    }
}

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
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

