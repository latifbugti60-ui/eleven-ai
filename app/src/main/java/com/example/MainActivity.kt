package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.EngineSettingsScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.screens.VoicesScreen
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate950
import com.example.viewmodel.ElevenVoiceViewModel
import com.example.viewmodel.StudioTab

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ElevenVoiceApp()
            }
        }
    }
}

data class NavigationTabItem(
    val tab: StudioTab,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun ElevenVoiceApp(
    viewModel: ElevenVoiceViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()

    // Handle system back button: return to Studio tab if on sub-screens
    BackHandler(enabled = currentTab != StudioTab.STUDIO) {
        viewModel.setTab(StudioTab.STUDIO)
    }

    val tabItems = listOf(
        NavigationTabItem(
            tab = StudioTab.STUDIO,
            label = "AI Studio",
            selectedIcon = Icons.Filled.GraphicEq,
            unselectedIcon = Icons.Outlined.GraphicEq,
            testTag = "tab_studio"
        ),
        NavigationTabItem(
            tab = StudioTab.VOICES,
            label = "Voice Lab",
            selectedIcon = Icons.Filled.RecordVoiceOver,
            unselectedIcon = Icons.Outlined.RecordVoiceOver,
            testTag = "tab_voices"
        ),
        NavigationTabItem(
            tab = StudioTab.HISTORY,
            label = "Library",
            selectedIcon = Icons.Filled.Headphones,
            unselectedIcon = Icons.Outlined.Headphones,
            testTag = "tab_history"
        ),
        NavigationTabItem(
            tab = StudioTab.SETTINGS,
            label = "Settings",
            selectedIcon = Icons.Filled.Tune,
            unselectedIcon = Icons.Outlined.Tune,
            testTag = "tab_settings"
        )
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isExpandedScreen = maxWidth >= 600.dp

        if (isExpandedScreen) {
            // Tablet / PC / Laptop Mode: Side Navigation Rail + Centered Content Area
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Slate950)
            ) {
                NavigationRail(
                    containerColor = Slate850,
                    modifier = Modifier.border(0.5.dp, Slate700, RoundedCornerShape(0.dp))
                ) {
                    tabItems.forEach { item ->
                        val isSelected = currentTab == item.tab
                        NavigationRailItem(
                            selected = isSelected,
                            onClick = { viewModel.setTab(item.tab) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = NeonCyan,
                                unselectedIconColor = Slate400,
                                unselectedTextColor = Slate400,
                                indicatorColor = ElectricViolet
                            ),
                            modifier = Modifier.testTag(item.testTag)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Slate950),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 840.dp)
                    ) {
                        AnimatedContent(
                            targetState = currentTab,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "ScreenTransitionWide"
                        ) { target ->
                            when (target) {
                                StudioTab.STUDIO -> StudioScreen(
                                    viewModel = viewModel,
                                    onNavigateToVoices = { viewModel.setTab(StudioTab.VOICES) }
                                )
                                StudioTab.VOICES -> VoicesScreen(viewModel = viewModel)
                                StudioTab.HISTORY -> HistoryScreen(viewModel = viewModel)
                                StudioTab.SETTINGS -> EngineSettingsScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        } else {
            // Mobile Phone Mode: Bottom Navigation Bar + Full Vertical Screen
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Slate950,
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .border(0.5.dp, Slate700, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                        containerColor = Slate850,
                        tonalElevation = 8.dp
                    ) {
                        tabItems.forEach { item ->
                            val isSelected = currentTab == item.tab
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { viewModel.setTab(item.tab) },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        fontSize = 11.sp
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    selectedTextColor = NeonCyan,
                                    unselectedIconColor = Slate400,
                                    unselectedTextColor = Slate400,
                                    indicatorColor = ElectricViolet
                                ),
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    AnimatedContent(
                        targetState = currentTab,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "ScreenTransitionMobile"
                    ) { target ->
                        when (target) {
                            StudioTab.STUDIO -> StudioScreen(
                                viewModel = viewModel,
                                onNavigateToVoices = { viewModel.setTab(StudioTab.VOICES) }
                            )
                            StudioTab.VOICES -> VoicesScreen(viewModel = viewModel)
                            StudioTab.HISTORY -> HistoryScreen(viewModel = viewModel)
                            StudioTab.SETTINGS -> EngineSettingsScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
