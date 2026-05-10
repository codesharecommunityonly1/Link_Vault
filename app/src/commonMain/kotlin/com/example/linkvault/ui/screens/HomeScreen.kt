package com.example.linkvault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.linkvault.data.LinkEntity
import com.example.linkvault.ui.viewmodel.LinkViewModel
import com.example.linkvault.ui.components.OfflineReaderDialog
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: LinkViewModel,
    onShowAbout: () -> Unit
) {
    val links by viewModel.links.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    var readingLink by remember { mutableStateOf<LinkEntity?>(null) }
    var currentTab by remember { mutableStateOf("links") }
    var showAddLinkDialog by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            // Mobile-style top bar for unified look, or could use Desktop Header
        }
    ) { padding ->
        Row(modifier = Modifier.fillMaxSize().padding(padding)) {
            NavigationRail(
                modifier = Modifier.fillMaxHeight(),
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                NavigationRailItem(
                    selected = currentTab == "links",
                    onClick = { 
                        currentTab = "links"
                        viewModel.setVaultSelected(false)
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Links") },
                    label = { Text("Links") }
                )
                NavigationRailItem(
                    selected = currentTab == "notes",
                    onClick = { currentTab = "notes" },
                    icon = { Icon(Icons.Default.Edit, contentDescription = "Notes") },
                    label = { Text("Notes") }
                )
                NavigationRailItem(
                    selected = currentTab == "feedback",
                    onClick = { currentTab = "feedback" },
                    icon = { Icon(Icons.Default.Email, contentDescription = "Feedback") },
                    label = { Text("Feedback") }
                )
                NavigationRailItem(
                    selected = currentTab == "vault",
                    onClick = { 
                        currentTab = "links"
                        viewModel.setVaultSelected(true)
                    },
                    icon = { Icon(Icons.Default.Lock, contentDescription = "Vault") },
                    label = { Text("Vault") }
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                NavigationRailItem(
                    selected = false,
                    onClick = {
                        viewModel.exportBackup()
                        scope.launch { snackbarHostState.showSnackbar("Backup saved to Recovery Folder") }
                    },
                    icon = { Icon(Icons.Default.Backup, contentDescription = "Backup") },
                    label = { Text("Backup") }
                )

                NavigationRailItem(
                    selected = false,
                    onClick = onShowAbout,
                    icon = { Icon(Icons.Default.Info, contentDescription = "About") },
                    label = { Text("About") }
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                when (currentTab) {
                    "links" -> {
                        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = searchQuery,
                                    onValueChange = { viewModel.onSearchQueryChange(it) },
                                    placeholder = { Text("Search links...") },
                                    modifier = Modifier.weight(1f),
                                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                                )
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                Button(onClick = { showAddLinkDialog = true }) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Add Link")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(links) { link ->
                                    DesktopLinkItem(
                                        link = link,
                                        onRead = { readingLink = link },
                                        onDelete = { viewModel.deleteLink(link) },
                                        onTogglePrivacy = { viewModel.togglePrivacy(link) }
                                    )
                                }
                            }
                        }
                    }
                    "notes" -> {
                        NotesScreen(viewModel = viewModel)
                    }
                    "feedback" -> {
                        FeedbackScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }

    if (showAddLinkDialog) {
        AddLinkDialog(
            onDismiss = { showAddLinkDialog = false },
            onAdd = { url, isPrivate ->
                viewModel.saveLink(url = url, isPrivate = isPrivate)
                showAddLinkDialog = false
            }
        )
    }

    readingLink?.let { link ->
        OfflineReaderDialog(link = link, onDismiss = { readingLink = null })
    }
}

@Composable
fun AddLinkDialog(onDismiss: () -> Unit, onAdd: (String, Boolean) -> Unit) {
    var url by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Save New Link") },
        text = {
            Column {
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Website URL") },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("https://...") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isPrivate, onCheckedChange = { isPrivate = it })
                    Text("Save to Private Vault")
                }
            }
        },
        confirmButton = {
            Button(onClick = { if (url.isNotBlank()) onAdd(url, isPrivate) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun DesktopLinkItem(
    link: LinkEntity,
    onRead: () -> Unit,
    onDelete: () -> Unit,
    onTogglePrivacy: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = link.title, style = MaterialTheme.typography.titleMedium)
                Text(text = link.url, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            
            Row {
                if (link.content != null) {
                    IconButton(onClick = onRead) {
                        Icon(Icons.Default.List, contentDescription = "Read")
                    }
                }
                IconButton(onClick = onTogglePrivacy) {
                    Icon(
                        if (link.isPrivate) Icons.Default.LockOpen else Icons.Default.Lock,
                        contentDescription = "Privacy"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
