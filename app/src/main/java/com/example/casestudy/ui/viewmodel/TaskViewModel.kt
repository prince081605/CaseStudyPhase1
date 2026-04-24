package com.example.casestudy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.casestudy.data.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TaskViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("tasks")

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks

    init {
        observeTasks()
    }

    private fun observeTasks() {
        tasksCollection.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            val tasks = snapshot?.toObjects(Task::class.java) ?: emptyList()
            _allTasks.value = tasks
        }
    }

    fun addTask(title: String, date: String) {
        viewModelScope.launch {
            val docRef = tasksCollection.document()
            val task = Task(id = docRef.id, title = title, date = date)
            docRef.set(task).await()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            tasksCollection.document(task.id).set(task).await()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            tasksCollection.document(task.id).delete().await()
        }
    }
}
