package com.andriawan.hydrationtracker.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andriawan.hydrationtracker.MainActivity
import com.andriawan.hydrationtracker.theme.HydrationTrackerTheme

@Composable
fun PercentageProgress(
    value: Int,
    maxValue: Int = 100,
    progressSize: Dp = 200.dp,
    foregroundStrokeWidth: Float = 12F,
    backgroundStrokeWidth: Float = 4F,
    indicatorForegroundColor: Color = MaterialTheme.colors.onBackground,
    indicatorBackgroundColor: Color = MaterialTheme.colors.onBackground
) {

    // Create animation for the percentage progress
    val allowedIndicatorValue = if (value < maxValue) value else maxValue
    var animatedIndicatorValue by remember {
        mutableFloatStateOf(0F)
    }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }
    val percentageValue = (animatedIndicatorValue / maxValue) * 100
    val sweepAngleValue by animateFloatAsState(
        targetValue = (3.6 * percentageValue).toFloat(),
        animationSpec = tween(1000),
        label = ""
    )

    // Create animation for text
    val animatedText by animateIntAsState(
        targetValue = percentageValue.toInt(),
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = ""
    )

    Column(
        modifier = Modifier
            .size(progressSize)
            .drawBehind {
                val componentSize = size / 1.25F
                backgroundDrawer(
                    componentSize = componentSize,
                    indicatorColor = indicatorBackgroundColor,
                    indicatorStrokeWidth = backgroundStrokeWidth
                )
                foregroundDrawer(
                    componentSize = componentSize,
                    indicatorStrokeWidth = foregroundStrokeWidth,
                    indicatorColor = indicatorForegroundColor,
                    sweepAngle = sweepAngleValue
                )
            }
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$animatedText%",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onBackground,
                fontSize = 48.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

fun DrawScope.foregroundDrawer(
    componentSize: Size,
    indicatorStrokeWidth: Float,
    indicatorColor: Color,
    sweepAngle: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        useCenter = false,
        startAngle = 270F,
        sweepAngle = sweepAngle,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2F,
            y = (size.height - componentSize.height) / 2F
        )
    )
}

fun DrawScope.backgroundDrawer(
    componentSize: Size,
    indicatorStrokeWidth: Float,
    indicatorColor: Color
) {
    drawArc(
        size = componentSize,
        startAngle = 90F,
        sweepAngle = 360F,
        useCenter = false,
        color = indicatorColor,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2F,
            y = (size.height - componentSize.height) / 2F
        )
    )
}

@Preview
@Composable
fun PercentageProgressPreview() {
    HydrationTrackerTheme {
        Surface {
            PercentageProgress(
                value = 80,
                maxValue = 100,
                indicatorForegroundColor = MaterialTheme.colors.onBackground,
                indicatorBackgroundColor = MaterialTheme.colors.onBackground,
                foregroundStrokeWidth = 8F,
                backgroundStrokeWidth = 4F
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PercentageProgressPreviewDarkMode() {
    HydrationTrackerTheme {
        Surface {
            PercentageProgress(
                value = 80,
                maxValue = 100,
                indicatorForegroundColor = MaterialTheme.colors.onBackground,
                indicatorBackgroundColor = MaterialTheme.colors.onBackground,
                foregroundStrokeWidth = 8F,
                backgroundStrokeWidth = 4F
            )
        }
    }
}
