package com.example.linkvault.`data`

import androidx.room.RoomDatabaseConstructor

public actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
  actual override fun initialize(): AppDatabase = com.example.linkvault.`data`.AppDatabase_Impl()
}
