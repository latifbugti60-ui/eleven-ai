package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.data.model.VoicePersona
import com.example.ui.components.CreateVoiceDialog
import com.example.ui.components.VoiceCard
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.viewmodel.ElevenVoiceViewModel
import com.example.viewmodel.StudioTab

@Composable
fun VoicesScreen(
    viewModel: ElevenVoiceViewModel,
    modifier: Modifier = Modifier
) {
    val allVoices by viewModel.allVoices.collectAsState()
    val selectedVoice by viewModel.selectedVoice.collectAsState()
    val isPreviewPlaying by viewModel.isPreviewPlaying.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showCloneModal by remember { mutableStateOf(false) }

    val categories = listOf("All", "My Cloned Voices", "Narration", "Conversational", "Cinematic", "ASMR & Soft", "Characters", "Roman Urdu", "Urdu / Hindi")

    val filteredVoices = allVoices.filter { voice ->
        val matchesCategory = when (selectedCategory) {
            "All" -> true
            "My Cloned Voices" -> voice.isClonedFromMic || voice.category == "My Cloned Voices"
            else -> voice.category.equals(selectedCategory, ignoreCase = true)
        }
        val matchesSearch = if (searchQuery.isBlank()) true else {
            voice.name.contains(searchQuery, ignoreCase = true) ||
            voice.accent.contains(searchQuery, ignoreCase = true) ||
            voice.toneTag.contains(searchQuery, ignoreCase = true) ||
            voice.description.contains(searchQuery, ignoreCase = true)
        }
        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950)
            .padding(top = 12.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Voice Lab",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Explore AI Personas & Voice Cloning",
                    color = Slate400,
                    fontSize = 12.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { showCloneModal = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricViolet,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .testTag("open_clone_voice_dialog_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Clone My Voice", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by name, accent, tone or character...", color = Slate700, fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Slate400,
                    modifier = Modifier.size(18.dp)
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ElectricViolet,
                unfocusedBorderColor = Slate700,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Slate850,
                unfocusedContainerColor = Slate850
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
                .testTag("voice_search_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Categories Scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isSel = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSel) NeonCyan else Slate850)
                        .border(1.dp, if (isSel) NeonCyan else Slate700, RoundedCornerShape(10.dp))
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = cat,
                        color = if (isSel) Color.Black else Slate200,
                        fontSize = 12.sp,
                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Voices List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(filteredVoices, key = { it.id }) { voice ->
                VoiceCard(
                    voice = voice,
                    isSelected = selectedVoice.id == voice.id,
                    isPreviewPlaying = isPreviewPlaying == voice.id,
                    onSelect = {
                        viewModel.selectVoice(voice)
                        viewModel.setTab(StudioTab.STUDIO)
                    },
                    onPreviewToggle = {
                        viewModel.previewVoice(voice)
                    }
                )
            }

            if (filteredVoices.isEmpty()) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                    ) {
                        Text(
                            text = "No voices match your search.",
                            color = Slate400,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }

    if (showCloneModal) {
        com.example.ui.components.VoiceCloningModal(
            onDismiss = { showCloneModal = false },
            onVoiceCloned = { name, gender, tone, pitch, speed, audioPath ->
                viewModel.cloneMyVoice(name, gender, tone, pitch, speed, audioPath)
                viewModel.setTab(StudioTab.STUDIO)
            }
        )
    }

    if (showCreateDialog) {
        CreateVoiceDialog(
            onDismiss = { showCreateDialog = false },
            onConfirm = { name, accent, tone, desc, gender, cat, pitch, speed, quote, locale ->
                viewModel.addCustomVoice(
                    name = name,
                    accent = accent,
                    tone = tone,
                    description = desc,
                    gender = gender,
                    category = cat,
                    pitch = pitch,
                    speed = speed,
                    sampleQuote = quote,
                    localeTag = locale
                )
            }
        )
    }
}
