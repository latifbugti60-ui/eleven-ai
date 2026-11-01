package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VoiceSettings
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import java.util.Locale

@Composable
fun ElevenVoiceSliders(
    settings: VoiceSettings,
    onStabilityChange: (Float) -> Unit,
    onSimilarityChange: (Float) -> Unit,
    onStyleChange: (Float) -> Unit,
    onSpeedChange: (Float) -> Unit,
    onPitchChange: (Float) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Slate850)
            .border(1.dp, Slate700, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Voice Settings",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Fine-tune clarity, stability, pace & tone",
                    color = Slate400,
                    fontSize = 12.sp
                )
            }

            IconButton(
                onClick = onReset,
                modifier = Modifier
                    .size(36.dp)
                    .testTag("reset_settings_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset sliders to default",
                    tint = Slate400,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Stability Slider
        VoiceSliderItem(
            title = "Stability",
            valueText = "${(settings.stability * 100).toInt()}%",
            leftHint = "More variable",
            rightHint = "More stable",
            value = settings.stability,
            onValueChange = onStabilityChange,
            valueRange = 0f..1f,
            activeColor = ElectricViolet,
            testTag = "stability_slider"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Similarity / Clarity Slider
        VoiceSliderItem(
            title = "Clarity + Similarity",
            valueText = "${(settings.similarityBoost * 100).toInt()}%",
            leftHint = "Low",
            rightHint = "High boost",
            value = settings.similarityBoost,
            onValueChange = onSimilarityChange,
            valueRange = 0f..1f,
            activeColor = NeonCyan,
            testTag = "similarity_slider"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Style Exaggeration Slider
        VoiceSliderItem(
            title = "Style Exaggeration",
            valueText = "${(settings.styleExaggeration * 100).toInt()}%",
            leftHint = "Subtle",
            rightHint = "Dramatic",
            value = settings.styleExaggeration,
            onValueChange = onStyleChange,
            valueRange = 0f..1f,
            activeColor = Color(0xFFF43F5E),
            testTag = "style_slider"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Speed / Pace Slider
        VoiceSliderItem(
            title = "Speech Speed",
            valueText = String.format(Locale.US, "%.2fx", settings.speed),
            leftHint = "0.5x Slow",
            rightHint = "2.0x Fast",
            value = settings.speed,
            onValueChange = onSpeedChange,
            valueRange = 0.5f..2.0f,
            activeColor = Color(0xFFF59E0B),
            testTag = "speed_slider"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Pitch Slider
        VoiceSliderItem(
            title = "Pitch Offset",
            valueText = String.format(Locale.US, "%.2fx", settings.pitch),
            leftHint = "Deeper",
            rightHint = "Higher",
            value = settings.pitch,
            onValueChange = onPitchChange,
            valueRange = 0.6f..1.6f,
            activeColor = Color(0xFF10B981),
            testTag = "pitch_slider"
        )
    }
}

@Composable
fun VoiceSliderItem(
    title: String,
    valueText: String,
    leftHint: String,
    rightHint: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    activeColor: Color,
    testTag: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Slate200,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Slate800)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = valueText,
                    color = activeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = activeColor,
                activeTrackColor = activeColor,
                inactiveTrackColor = Slate700
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = leftHint,
                color = Slate400,
                fontSize = 10.sp
            )
            Text(
                text = rightHint,
                color = Slate400,
                fontSize = 10.sp
            )
        }
    }
}
