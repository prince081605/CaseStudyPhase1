
package com.example.casestudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudy.data.Announcement
import com.example.casestudy.data.AppDao
import com.example.casestudy.data.Task
import com.example.casestudy.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao: AppDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(sessionManager.isDarkMode())
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    fun toggleDarkMode(enabled: Boolean) {
        sessionManager.setDarkMode(enabled)
        _isDarkMode.value = enabled
    }

    val tasks = dao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements = dao.getAllAnnouncements()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTask(task: Task) {
        viewModelScope.launch {
            dao.insertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            dao.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
        }
    }

    fun updateAnnouncement(announcement: Announcement) {
        viewModelScope.launch {
            dao.updateAnnouncement(announcement)
        }
    }
}
