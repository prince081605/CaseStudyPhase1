package com.example.casestudy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = "", // Changed to String for Firestore ID
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val isCompleted: Boolean = false
)
