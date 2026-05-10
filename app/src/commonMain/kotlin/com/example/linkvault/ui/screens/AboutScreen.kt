package com.example.linkvault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkvault.util.PlatformUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Link Vault") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "Link Vault",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Secure, Local-First Link Management (Windows Desktop)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle("Key Features")
            FeatureItem("Offline Snapshots", "Read your saved articles even without an internet connection.")
            FeatureItem("AES-GCM Security", "Keep your private links safe with industry-standard encryption.")
            FeatureItem("Modern Notes", "Manage your thoughts alongside your links.")
            FeatureItem("Feedback Form", "Direct line to the developer for suggestions.")

            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle("Terms & Privacy")
            Text(
                text = "Link Vault is designed with privacy at its core. All your data, links, and snapshots are stored locally on your device. We do not have servers, and we never collect or share your information.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            SectionTitle("About Developer")
            Text(
                text = "VINAYAK KUMAR",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialIcon(Icons.Default.Email) { PlatformUtil.openUrl("mailto:codesharecommunityonly1@gmail.com") }
                SocialIcon(Icons.Default.Person) { PlatformUtil.openUrl("https://www.instagram.com/devine.technophile/") }
                SocialIcon(Icons.Default.Build) { PlatformUtil.openUrl("https://github.com/codesharecommunityonly1") }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun FeatureItem(title: String, description: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Text(text = description, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun SocialIcon(icon: ImageVector, onClick: () -> Unit) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Icon(icon, contentDescription = null)
    }
}
