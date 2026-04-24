package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.casestudy.data.Task
import com.example.casestudy.ui.viewmodel.TaskViewModel
import java.util.*
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {

    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    val blackBg = Color(0xFF0F0F0F)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Academic Tasks", color = white) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = white)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blackBg)
            )
        },
        containerColor = blackBg,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = cyan
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.Black)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF0B0B0B), Color(0xFF111111))
                        )
                    )
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.08f), 150f, Offset(size.width * 0.2f, size.height * 0.15f))
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.06f), 200f, Offset(size.width * 0.8f, size.height * 0.3f))
            }

            if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tasks yet. Tap + to add one.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onDelete = { viewModel.deleteTask(task) },
                            onEdit = { editingTask = task },
                            cyan = cyan,
                            white = white
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        TaskDialog(
            title = "New Task",
            onDismiss = { showAddDialog = false },
            onConfirm = { title, date ->
                viewModel.addTask(title, date)
                showAddDialog = false
            }
        )
    }

    editingTask?.let { task ->
        TaskDialog(
            title = "Edit Task",
            initialTitle = task.title,
            initialDate = task.date,
            onDismiss = { editingTask = null },
            onConfirm = { title, date ->
                viewModel.updateTask(task.copy(title = title, date = date))
                editingTask = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialog(
    title: String,
    initialTitle: String = "",
    initialDate: String = "",
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var taskTitle by remember { mutableStateOf(initialTitle) }
    var taskDate by remember { mutableStateOf(initialDate) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val date = datePickerState.selectedDateMillis?.let {
                        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        sdf.format(Date(it))
                    } ?: ""
                    taskDate = date
                    showDatePicker = false
                    showTimePicker = true
                }) { Text("OK", color = cyan) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel", color = cyan) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val time = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                    taskDate = if (taskDate.isNotEmpty()) "$taskDate at $time" else time
                    showTimePicker = false
                }) { Text("OK", color = cyan) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel", color = cyan) }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, color = white) },
        containerColor = darkCard,
        text = {
            Column {
                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Task Title", color = white) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = white,
                        unfocusedTextColor = white,
                        focusedBorderColor = cyan,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = taskDate,
                    onValueChange = { taskDate = it },
                    label = { Text("Date & Time", color = white) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.Event, contentDescription = "Select Date", tint = cyan)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = white,
                        unfocusedTextColor = white,
                        focusedBorderColor = cyan,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (taskTitle.isNotBlank()) {
                        onConfirm(taskTitle, taskDate)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = cyan)
            ) {
                Text("Save", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = cyan)
            }
        }
    )
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit, onEdit: () -> Unit, cyan: Color, white: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEdit() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF222222)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.Event, contentDescription = null, tint = cyan)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(task.title, color = white, style = MaterialTheme.typography.titleMedium)
                    if (task.date.isNotEmpty()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(task.date, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = cyan)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Gray)
                }
            }
        }
    }
}
