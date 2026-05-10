package com.example.linkvault.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "links")
data class LinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    @ColumnInfo(name = "collection_name") val collectionName: String? = null,
    @ColumnInfo(name = "is_dead") val isDead: Boolean = false,
    @ColumnInfo(name = "is_duplicate") val isDuplicate: Boolean = false,
    @ColumnInfo(name = "is_private") val isPrivate: Boolean = false,
    @ColumnInfo(name = "reading_time") val readingTime: Int? = null,
    @ColumnInfo(name = "is_video") val isVideo: Boolean = false,
    val content: String? = null,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis()
)
