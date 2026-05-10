package com.example.linkvault.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.ConstructedBy
import androidx.room.RoomDatabaseConstructor

@Database(entities = [LinkEntity::class, LinkFtsEntity::class, NoteEntity::class], version = 7, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
    abstract fun noteDao(): NoteDao
}

// Room Multiplatform Constructor
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>

expect fun getAppDatabase(): AppDatabase
