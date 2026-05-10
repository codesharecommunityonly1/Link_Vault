package com.example.linkvault.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "is_pinned") val isPinned: Boolean = false,
    val color: Long? = null,
    val tags: String? = null,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)
