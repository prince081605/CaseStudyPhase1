package com.example.casestudy.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class, Announcement::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao
}
