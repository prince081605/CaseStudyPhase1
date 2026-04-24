package com.example.casestudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudy.data.Announcement
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AnnouncementViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val announcementsCollection = firestore.collection("announcements")

    private val _allAnnouncements = MutableStateFlow<List<Announcement>>(emptyList())
    val allAnnouncements: StateFlow<List<Announcement>> = _allAnnouncements

    init {
        observeAnnouncements()
    }

    private fun observeAnnouncements() {
        announcementsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            val announcements = snapshot?.toObjects(Announcement::class.java) ?: emptyList()
            _allAnnouncements.value = announcements
        }
    }

    fun addAnnouncement(title: String, content: String, date: String) {
        viewModelScope.launch {
            val docRef = announcementsCollection.document()
            val announcement = Announcement(
                id = docRef.id,
                title = title,
                content = content,
                date = date,
                author = "Admin"
            )
            docRef.set(announcement).await()
        }
    }

    fun markAsRead(announcement: Announcement) {
        viewModelScope.launch {
            announcementsCollection.document(announcement.id)
                .update("isRead", true).await()
        }
    }
}
