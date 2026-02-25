package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.casestudy.data.Task
import com.example.casestudy.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavController, viewModel: MainViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

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
                onClick = { 
                    taskToEdit = null
                    showAddDialog = true 
                },
                containerColor = cyan,
                contentColor = white
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0B0B0B), Color(0xFF111111))
                    )
                )
        ) {
            if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tasks yet. Tap + to add one!", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onToggle = { viewModel.updateTask(task.copy(isCompleted = !task.isCompleted)) },
                            onDelete = { viewModel.deleteTask(task) },
                            onEdit = {
                                taskToEdit = task
                                showAddDialog = true
                            }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            TaskDialog(
                task = taskToEdit,
                onDismiss = { showAddDialog = false },
                onSave = { title, dateTime ->
                    if (taskToEdit == null) {
                        viewModel.addTask(Task(title = title, description = "", dueDate = dateTime))
                    } else {
                        viewModel.updateTask(taskToEdit!!.copy(title = title, dueDate = dateTime))
                    }
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggle: () -> Unit, onDelete: () -> Unit, onEdit: () -> Unit) {
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEdit() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = darkCard),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = cyan, uncheckedColor = Color.Gray)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    color = white,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Due: ${task.dueDate}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = cyan.copy(alpha = 0.7f))
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialog(task: Task? = null, onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    
    val sdfDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val sdfTime = SimpleDateFormat("hh:mm a", Locale.getDefault())

    var selectedDate by remember { 
        mutableStateOf(if (task != null) task.dueDate.split(" ").take(3).joinToString(" ") else sdfDate.format(Date())) 
    }
    var selectedTime by remember { 
        mutableStateOf(if (task != null) task.dueDate.split(" ").drop(3).joinToString(" ") else "12:00 PM") 
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = if (task != null) {
            try { sdfDate.parse(selectedDate)?.time } catch (e: Exception) { null }
        } else null
    )
    
    val initialHourAndMinute = if (task != null) {
        try {
            val cal = Calendar.getInstance()
            cal.time = sdfTime.parse(selectedTime)!!
            Pair(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
        } catch (e: Exception) { Pair(12, 0) }
    } else Pair(12, 0)

    val timePickerState = rememberTimePickerState(
        initialHour = initialHourAndMinute.first,
        initialMinute = initialHourAndMinute.second
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (task == null) "New Academic Task" else "Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Event, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(selectedDate, modifier = Modifier.weight(1f))
                    TextButton(onClick = { showDatePicker = true }) {
                        Text("Pick Date")
                    }
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(selectedTime, modifier = Modifier.weight(1f))
                    TextButton(onClick = { showTimePicker = true }) {
                        Text("Pick Time")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank()) onSave(title, "$selectedDate $selectedTime") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
            ) {
                Text(if (task == null) "Add" else "Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = sdfDate.format(Date(it))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
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
                    val cal = Calendar.getInstance()
                    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    cal.set(Calendar.MINUTE, timePickerState.minute)
                    selectedTime = sdfTime.format(cal.time)
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }
}
