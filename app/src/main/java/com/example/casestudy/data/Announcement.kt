package com.example.casestudy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey val id: String = "", // Changed to String for Firestore ID
    val title: String = "",
    val content: String = "",
    val date: String = "",
    val isRead: Boolean = false,
    val author: String = "Admin"
)
