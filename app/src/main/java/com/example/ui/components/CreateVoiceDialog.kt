package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import java.util.Locale

@Composable
fun CreateVoiceDialog(
    onDismiss: () -> Unit,
    onConfirm: (
        name: String,
        accent: String,
        tone: String,
        description: String,
        gender: String,
        category: String,
        pitch: Float,
        speed: Float,
        sampleQuote: String,
        localeTag: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var accent by remember { mutableStateOf("Urdu / Bilingual") }
    var tone by remember { mutableStateOf("Deep & Cinematic") }
    var description by remember { mutableStateOf("Custom cloned neural voice profile") }
    var gender by remember { mutableStateOf("Male") }
    var category by remember { mutableStateOf("Urdu / Hindi") }
    var pitch by remember { mutableFloatStateOf(1.0f) }
    var speed by remember { mutableFloatStateOf(1.0f) }
    var sampleQuote by remember { mutableStateOf("یہ میری اپنی تیار کردہ جدید اے آئی آواز ہے۔") }
    var selectedLocale by remember { mutableStateOf("ur-PK") }

    val accentOptions = listOf(
        "Urdu / Bilingual" to "ur-PK",
        "American" to "en-US",
        "British" to "en-GB",
        "Australian" to "en-AU",
        "Hindi / South Asian" to "hi-IN"
    )

    val categoryOptions = listOf("Urdu / Hindi", "Narration", "Conversational", "Cinematic", "Characters")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Slate900,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .border(1.dp, Slate700, RoundedCornerShape(24.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Design AI Voice",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Voice Lab Cloning & Custom Profile",
                            color = NeonCyan,
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Slate400
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Voice Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Voice Name", color = Slate400) },
                    placeholder = { Text("e.g. Latif Narrator / Aurora AI", color = Slate700) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricViolet,
                        unfocusedBorderColor = Slate700,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Slate850,
                        unfocusedContainerColor = Slate850
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_voice_name_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Gender Selection
                Text("Gender", color = Slate200, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Male", "Female").forEach { g ->
                        val isSel = gender == g
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSel) ElectricViolet else Slate850)
                                .border(1.dp, if (isSel) ElectricViolet else Slate700, RoundedCornerShape(10.dp))
                                .clickable { gender = g }
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = g,
                                color = if (isSel) Color.White else Slate400,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Accent & Locale
                Text("Accent & Language", color = Slate200, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    accentOptions.forEach { (acc, loc) ->
                        val isSel = accent == acc
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) Slate800 else Slate850)
                                .clickable {
                                    accent = acc
                                    selectedLocale = loc
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = acc,
                                color = if (isSel) NeonCyan else Slate400,
                                fontSize = 13.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Category chips
                Text("Category", color = Slate200, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categoryOptions.take(3).forEach { cat ->
                        val isSel = category == cat
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) NeonCyan else Slate850)
                                .clickable { category = cat }
                                .padding(vertical = 6.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSel) Color.Black else Slate400,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pitch Slider
                Text("Base Pitch: ${String.format(Locale.US, "%.2fx", pitch)}", color = Slate200, fontSize = 12.sp)
                Slider(
                    value = pitch,
                    onValueChange = { pitch = it },
                    valueRange = 0.6f..1.6f,
                    colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan)
                )

                // Speed Slider
                Text("Base Speed: ${String.format(Locale.US, "%.2fx", speed)}", color = Slate200, fontSize = 12.sp)
                Slider(
                    value = speed,
                    onValueChange = { speed = it },
                    valueRange = 0.6f..1.6f,
                    colors = SliderDefaults.colors(thumbColor = ElectricViolet, activeTrackColor = ElectricViolet)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Sample quote
                OutlinedTextField(
                    value = sampleQuote,
                    onValueChange = { sampleQuote = it },
                    label = { Text("Sample Quote / Preview Text", color = Slate400) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricViolet,
                        unfocusedBorderColor = Slate700,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = Slate850,
                        unfocusedContainerColor = Slate850
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val finalName = if (name.isBlank()) "Custom Voice" else name.trim()
                        onConfirm(
                            finalName,
                            accent,
                            tone,
                            description,
                            gender,
                            category,
                            pitch,
                            speed,
                            sampleQuote,
                            selectedLocale
                        )
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricViolet,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_custom_voice_button")
                ) {
                    Text("Save to My Voice Lab", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
