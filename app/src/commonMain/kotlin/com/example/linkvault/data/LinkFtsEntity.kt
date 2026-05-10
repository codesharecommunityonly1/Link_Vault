package com.example.linkvault.data

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = LinkEntity::class)
@Entity(tableName = "links_fts")
data class LinkFtsEntity(
    val title: String,
    val description: String?,
    val url: String
)
