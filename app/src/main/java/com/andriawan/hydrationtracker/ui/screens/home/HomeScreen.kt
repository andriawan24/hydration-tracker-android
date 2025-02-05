package com.andriawan.hydrationtracker.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.andriawan.hydrationtracker.R
import com.andriawan.hydrationtracker.data.models.DrinkType
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme
import com.andriawan.hydrationtracker.ui.components.CustomSnackBar
import com.andriawan.hydrationtracker.ui.components.OptionCard
import com.andriawan.hydrationtracker.ui.components.OptionType
import com.andriawan.hydrationtracker.ui.components.PercentageProgress
import com.andriawan.hydrationtracker.ui.components.WavesAnimationBox
import com.andriawan.hydrationtracker.utils.Constants.ANALYTICS_ADD_WATER
import com.andriawan.hydrationtracker.utils.Constants.ANALYTICS_OTHER_OPTION
import com.andriawan.hydrationtracker.utils.Constants.ANALYTICS_REDUCE_WATER
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Date

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
        sheetShape = MaterialTheme.shapes.medium.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BottomSheetContent(
                    drinkTypes = state.drinkTypes.orEmpty(),
                    onOptionClicked = {
                        viewModel.addWater(it.amount)
                        scope.launch {
                            analytics.logEvent(
                                FirebaseAnalytics.Event.SELECT_ITEM,
                                bundleOf(ANALYTICS_ADD_WATER to it.amount.toString())
                            )
                            bottomSheetState.hide()
                            snackBarHostState.currentSnackbarData?.dismiss()
                            val result = snackBarHostState.showSnackbar(
                                message = context.getString(R.string.water_added, it.name),
                                duration = SnackbarDuration.Short,
                                actionLabel = context.getString(R.string.undo)
                            )

                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    analytics.logEvent(
                                        FirebaseAnalytics.Event.SELECT_ITEM,
                                        bundleOf(ANALYTICS_REDUCE_WATER to it.amount.toString())
                                    )
                                    viewModel.reduceWater(it.amount)
                                }

                                SnackbarResult.Dismissed -> {
                                    snackBarHostState.currentSnackbarData?.dismiss()
                                }
                            }
                        }
                    }
                )
            }
        }
    ) {
        MainScreen(
            onOptionClicked = {
                viewModel.addWater(it.amount)
                scope.launch {
                    analytics.logEvent(
                        FirebaseAnalytics.Event.SELECT_ITEM,
                        bundleOf(ANALYTICS_ADD_WATER to it.amount.toString())
                    )

                    snackBarHostState.currentSnackbarData?.dismiss()
                    val result = snackBarHostState.showSnackbar(
                        message = context.getString(R.string.water_added, it.name),
                        duration = SnackbarDuration.Short,
                        actionLabel = context.getString(R.string.undo)
                    )

                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            analytics.logEvent(
                                FirebaseAnalytics.Event.SELECT_ITEM,
                                bundleOf(ANALYTICS_REDUCE_WATER to it.amount.toString())
                            )
                            viewModel.reduceWater(it.amount)
                        }

                        SnackbarResult.Dismissed -> {
                            snackBarHostState.currentSnackbarData?.dismiss()
                        }
                    }
                }
            },
            onOtherOptionClicked = {
                analytics.logEvent(
                    FirebaseAnalytics.Event.SELECT_ITEM,
                    bundleOf(ANALYTICS_OTHER_OPTION to Date().toString())
                )

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
fun BottomSheetContent(
    drinkTypes: List<DrinkType>,
    onOptionClicked: (DrinkType) -> Unit
) {
    Text(text = stringResource(R.string.all_option))
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 4),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = drinkTypes,
            key = {
                it.name
            }
        ) {
            OptionCard(
                onCardClicked = { onOptionClicked(it) },
                title = it.name,
                icon = it.icon,
                type = OptionType.COMMON_OPTION,
                modifier = Modifier.height(75.dp)
            )
        }
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
                CustomSnackBar(state = snackBarHostState)
                OptionList(
                    drinkTypes = state.drinkTypes?.take(3),
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

