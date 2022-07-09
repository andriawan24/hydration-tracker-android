package com.andriawan.hydrationtracker.ui.components

import android.graphics.Matrix
import android.graphics.Shader
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun WavesAnimationBox(
    modifier: Modifier,
    color: Color,
    progress: Float
) {
    BoxWithConstraints(modifier = modifier.offset(y = 16.dp), contentAlignment = Alignment.Center) {
        val constraintsWidth = maxWidth
        val constraintsHeight = maxHeight
        val density = LocalDensity.current

        val waveShader by produceState<Shader?>(
            initialValue = null,
            constraintsHeight,
            constraintsWidth,
            color,
            density
        ) {
            value = withContext(Dispatchers.Default) {
                createsWaveShader(
                    width = with(density) { constraintsWidth.roundToPx() },
                    height = with(density) { constraintsHeight.roundToPx() },
                    color = color
                )
            }
        }

        Log.i("Waves Shader", "Wave shader $waveShader")
        if (progress > 0 && waveShader != null) {
            WavesOnCanvas(
                shader = waveShader!!,
                progress = progress.coerceAtMost(1F)
            )
        }
    }
}

@Composable
private fun WavesOnCanvas(shader: Shader, progress: Float) {
    val matrix = remember { Matrix() }

    val paint = remember(shader) {
        Paint().apply {
            isAntiAlias = true
            this.shader = shader
        }
    }

    val wavesTransition = rememberWavesTransition()
    val amplitudeRatio by wavesTransition.amplitudeRatio
    val wavesShiftRatio by wavesTransition.wavesShiftRatio

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas {
            val height = size.height
            val width = size.width
            matrix.setScale(1F, amplitudeRatio / AmplitudeRatio, 0F, WaterLevelRatio * height)
            matrix.postTranslate(wavesShiftRatio * width, (WaterLevelRatio - progress) * height)
            shader.setLocalMatrix(matrix)
            it.drawRect(0F, 0F, width, height, paint)
        }
    }
}

@Composable
private fun rememberWavesTransition(): WavesTransition {
    val transition = rememberInfiniteTransition()

    val waveShiftRatio = transition.animateFloat(
        initialValue = 0F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = WavesShiftAnimationDurationInMillis,
                easing = LinearEasing
            )
        )
    )

    val amplitudeRatio = transition.animateFloat(
        initialValue = 0.005F,
        targetValue = 0.015F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = WavesAmplitudeAnimationDurationInMillis,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    return remember(transition) {
        WavesTransition(
            wavesShiftRatio = waveShiftRatio,
            amplitudeRatio = amplitudeRatio
        )
    }
}

@Stable
private fun createsWaveShader(width: Int, height: Int, color: Color): Shader {
    val angularFrequency = 2F * PI / width
    val amplitude = height * AmplitudeRatio
    val waterLevel = height * WaterLevelRatio

    val bitmap = ImageBitmap(width = width, height = height, ImageBitmapConfig.Argb8888)
    val canvas = Canvas(bitmap)

    val wavePoint = Paint().apply {
        strokeWidth = 2F
        isAntiAlias = true
    }

    val waveY = FloatArray(size = width + 1)

    wavePoint.color = color.copy(alpha = 0.3F)
    for (beginX in 0..width) {
        val wx = beginX * angularFrequency
        val beginY = waterLevel + amplitude * sin(wx).toFloat()
        canvas.drawLine(
            p1 = Offset(x = beginX.toFloat(), y = beginY),
            p2 = Offset(x = beginX.toFloat(), y = (height + 1).toFloat()),
            paint = wavePoint
        )
        waveY[beginX] = beginY
    }

    wavePoint.color = color
    val endX = width + 1
    val waveToShift = width / 4
    for (beginX in 0..width) {
        canvas.drawLine(
            p1 = Offset(x = beginX.toFloat(), y = waveY[(beginX + waveToShift).rem(endX)]),
            p2 = Offset(x = beginX.toFloat(), y = (height + 1).toFloat()),
            paint = wavePoint
        )
    }

    return ImageShader(image = bitmap, tileModeX = TileMode.Repeated, tileModeY = TileMode.Clamp)
}

private class WavesTransition(
    val wavesShiftRatio: State<Float>,
    val amplitudeRatio: State<Float>
)

private const val AmplitudeRatio = 0.05f
private const val WaterLevelRatio = 0.5f
private const val WavesShiftAnimationDurationInMillis = 2500
private const val WavesAmplitudeAnimationDurationInMillis = 3000