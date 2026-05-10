package com.example.linkvault

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.example.linkvault.data.getAppDatabase
import com.example.linkvault.ui.screens.HomeScreen
import com.example.linkvault.ui.screens.AboutScreen
import com.example.linkvault.ui.screens.OnboardingScreen
import com.example.linkvault.ui.viewmodel.LinkViewModel
import com.example.linkvault.util.DesktopPrefs

@Composable
fun App() {
    var hasAgreed by remember { mutableStateOf(DesktopPrefs.hasAgreed()) }
    var currentScreen by remember { mutableStateOf("home") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (!hasAgreed) {
                OnboardingScreen(onAgreed = { recoveryPath ->
                    DesktopPrefs.setRecoveryPath(recoveryPath)
                    DesktopPrefs.setAgreed(true)
                    hasAgreed = true 
                })
            } else {
                // Initialize the real multiplatform database and ViewModel
                val database = remember { getAppDatabase() }
                val viewModel = remember { LinkViewModel(database) }
                
                when (currentScreen) {
                    "home" -> HomeScreen(
                        viewModel = viewModel,
                        onShowAbout = { currentScreen = "about" }
                    )
                    "about" -> AboutScreen(
                        onBack = { currentScreen = "home" }
                    )
                }
            }
        }
    }
}
