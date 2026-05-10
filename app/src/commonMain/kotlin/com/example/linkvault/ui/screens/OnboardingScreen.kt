package com.example.linkvault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.linkvault.util.PlatformUtil

@Composable
fun OnboardingScreen(onAgreed: (String) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }
    var selectedPath by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Setup Link Vault Recovery",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Choose a primary folder for your backups and data recovery. This folder will store your encrypted backups safely on your PC.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Recovery Folder Selection
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Primary Recovery Folder", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = selectedPath,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("No folder selected") }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Button(onClick = {
                            PlatformUtil.pickFolder()?.let {
                                selectedPath = it
                            }
                        }) {
                            Icon(Icons.Default.Folder, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Browse")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                ),
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Terms of Service",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "By using this app, you agree that all data is stored locally on your device. We do not collect or share your personal information. You are responsible for keeping your recovery folder safe.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                Text(
                    text = "I agree to the terms and understand the recovery setup",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onAgreed(selectedPath) },
                enabled = isChecked && selectedPath.isNotBlank(),
                modifier = Modifier.fillMaxWidth().widthIn(max = 600.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Complete Setup & Launch")
            }
        }
    }
}
