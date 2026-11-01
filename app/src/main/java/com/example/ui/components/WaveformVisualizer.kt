package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.CoralGlow

@Composable
fun WaveformVisualizer(
    amplitudes: List<Float>,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    barCount: Int = 24
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.283f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    val gradientBrush = remember {
        Brush.horizontalGradient(
            colors = listOf(
                NeonCyan,
                ElectricViolet,
                CoralGlow
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val totalWidth = size.width
        val canvasHeight = size.height
        val centerY = canvasHeight / 2f

        val spacing = totalWidth / barCount
        val barWidth = (spacing * 0.55f).coerceIn(3f, 14f)

        for (i in 0 until barCount) {
            val rawAmp = amplitudes.getOrElse(i) { 0.2f }
            val dynamicFactor = if (isPlaying) {
                val waveMod = Math.sin(phase.toDouble() + (i * 0.4)).toFloat() * 0.25f
                (rawAmp + waveMod).coerceIn(0.12f, 1.0f)
            } else {
                // Gentle idle breathing wave
                val idleWave = Math.sin((phase * 0.5) + (i * 0.25)).toFloat() * 0.08f
                (0.15f + idleWave).coerceIn(0.08f, 0.35f)
            }

            val barHeight = (canvasHeight * 0.85f * dynamicFactor).coerceAtLeast(6f)
            val left = (i * spacing) + (spacing - barWidth) / 2f
            val top = centerY - (barHeight / 2f)

            drawRoundRect(
                brush = gradientBrush,
                topLeft = Offset(left, top),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f),
                alpha = if (isPlaying) 0.95f else 0.45f
            )
        }
    }
}
