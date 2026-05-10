package com.example.linkvault.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.linkvault.data.NoteEntity
import com.example.linkvault.ui.viewmodel.LinkViewModel

@Composable
fun NotesScreen(viewModel: LinkViewModel) {
    val notes by viewModel.notes.collectAsState()
    val searchQuery by viewModel.noteSearchQuery.collectAsState()
    
    var showAddDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<NoteEntity?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onNoteSearchChange(it) },
                placeholder = { Text("Search notes...") },
                modifier = Modifier.weight(1f),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Button(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("New Note")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 250.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notes) { note ->
                NoteCard(
                    note = note,
                    onClick = { editingNote = note },
                    onDelete = { viewModel.deleteNote(note) },
                    onPin = { viewModel.toggleNotePin(note) }
                )
            }
        }
    }

    if (showAddDialog || editingNote != null) {
        NoteEditorDialog(
            note = editingNote,
            onDismiss = { 
                showAddDialog = false
                editingNote = null
            },
            onSave = { title, content ->
                viewModel.saveNote(title, content, editingNote?.isPinned ?: false)
                showAddDialog = false
                editingNote = null
            }
        )
    }
}

@Composable
fun NoteCard(
    note: NoteEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onPin: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onPin, modifier = Modifier.size(24.dp)) {
                    Icon(
                        if (note.isPinned) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Pin",
                        tint = if (note.isPinned) Color(0xFFFFD700) else LocalContentColor.current
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun NoteEditorDialog(
    note: NoteEntity?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (note == null) "New Note" else "Edit Note") },
        text = {
            Column(modifier = Modifier.fillMaxWidth().width(500.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Write your thoughts...") },
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = { if (title.isNotBlank()) onSave(title, content) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
