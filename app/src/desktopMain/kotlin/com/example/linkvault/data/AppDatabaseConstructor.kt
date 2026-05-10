package com.example.linkvault.data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import java.io.File

actual fun getAppDatabase(): AppDatabase {
    val dbFile = File(System.getProperty("user.home"), ".linkvault.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
     .build()
}