package com.example.linkvault.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.linkvault.data.LinkEntity

enum class ReaderTheme(val backgroundColor: Color, val textColor: Color, val label: String) {
    LIGHT(Color(0xFFFFFFFF), Color(0xFF1A1A1A), "Light"),
    SEPIA(Color(0xFFF4ECD8), Color(0xFF5B4636), "Sepia"),
    DARK(Color(0xFF121212), Color(0xFFE0E0E0), "Dark")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineReaderDialog(link: LinkEntity, onDismiss: () -> Unit) {
    var currentTheme by remember { mutableStateOf(ReaderTheme.LIGHT) }
    var fontSize by remember { mutableStateOf(18f) }
    var showSettings by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = currentTheme.backgroundColor
        ) {
            Scaffold(
                containerColor = currentTheme.backgroundColor,
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = currentTheme.backgroundColor,
                            titleContentColor = currentTheme.textColor,
                            navigationIconContentColor = currentTheme.textColor,
                            actionIconContentColor = currentTheme.textColor
                        ),
                        title = {
                            Text(
                                text = link.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        },
                        actions = {
                            IconButton(onClick = { showSettings = !showSettings }) {
                                Icon(Icons.Default.Settings, contentDescription = "Reader Settings")
                            }
                        }
                    )
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = link.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = currentTheme.textColor,
                                fontSize = (fontSize + 4).sp
                            ),
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = link.url,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (currentTheme == ReaderTheme.DARK) Color(0xFFBB86FC) else MaterialTheme.colorScheme.primary
                        )
                        
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = currentTheme.textColor.copy(alpha = 0.2f)
                        )
                        
                        if (link.content != null) {
                            Text(
                                text = link.content,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = currentTheme.textColor,
                                    fontSize = fontSize.sp,
                                    lineHeight = fontSize.sp * 1.6
                                )
                            )
                        } else {
                            Text(
                                text = "No offline content available for this link.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Red
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }

                    if (showSettings) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 16.dp)
                                .width(200.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Font Size", style = MaterialTheme.typography.labelMedium)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TextButton(onClick = { 
                                        if (fontSize > 12f) fontSize -= 2f
                                    }) { Text("A-", fontSize = 14.sp) }
                                    
                                    Text("${fontSize.toInt()}", style = MaterialTheme.typography.bodyMedium)
                                    
                                    TextButton(onClick = { 
                                        if (fontSize < 32f) fontSize += 2f
                                    }) { Text("A+", fontSize = 18.sp) }
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Theme", style = MaterialTheme.typography.labelMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    ReaderTheme.entries.forEach { theme ->
                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(theme.backgroundColor)
                                                .border(
                                                    width = if (currentTheme == theme) 2.dp else 1.dp,
                                                    color = if (currentTheme == theme) MaterialTheme.colorScheme.primary else Color.Gray,
                                                    shape = CircleShape
                                                )
                                                .clickable { currentTheme = theme }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
