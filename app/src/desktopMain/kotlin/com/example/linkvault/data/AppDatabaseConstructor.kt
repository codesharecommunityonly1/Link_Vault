package com.example.linkvault.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase = error("Use databaseBuilder instead")
}

actual fun getAppDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("user.home"), ".linkvault.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
        factory = { AppDatabaseConstructor.initialize() }
    ).setDriver(BundledSQLiteDriver())
     .build()
}
