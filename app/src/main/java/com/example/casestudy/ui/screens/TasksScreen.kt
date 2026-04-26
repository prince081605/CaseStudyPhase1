package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.casestudy.data.Task
import com.example.casestudy.ui.theme.*
import com.example.casestudy.ui.viewmodel.TaskViewModel
import com.example.casestudy.util.SessionManager
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val isDarkMode = sessionManager.isDarkMode()

    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    // Cartoonish Theme Colors
    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else DarkText
    val accentColor = MintGreen
    val secondaryAccent = PastelBlue
    val borderColor = if (isDarkMode) Color(0xFF333333) else DarkText.copy(alpha = 0.1f)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "My Tasks", 
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textColor)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = bgColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = accentColor,
                contentColor = DarkText,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = accentColor)
                }
            } else if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CheckCircle, null, tint = Color.Gray, modifier = Modifier.size(64.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("All tasks done! Yay!", color = Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
                ) {
                    items(tasks) { task ->
                        CartoonTaskItem(
                            task = task,
                            onDelete = { viewModel.deleteTask(task) },
                            onEdit = { editingTask = task },
                            cardBg = cardBg,
                            borderColor = borderColor,
                            textColor = textColor,
                            accentColor = secondaryAccent
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        CartoonTaskDialog(
            title = "New Task",
            onDismiss = { showAddDialog = false },
            onConfirm = { title, desc, date ->
                viewModel.addTask(title, desc, date)
                showAddDialog = false
            },
            isDarkMode = isDarkMode
        )
    }

    editingTask?.let { task ->
        CartoonTaskDialog(
            title = "Edit Task",
            initialTitle = task.title,
            initialDescription = task.description,
            initialDate = task.date,
            onDismiss = { editingTask = null },
            onConfirm = { title, desc, date ->
                viewModel.updateTask(task.copy(title = title, description = desc, date = date))
                editingTask = null
            },
            isDarkMode = isDarkMode
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonTaskDialog(
    title: String,
    initialTitle: String = "",
    initialDescription: String = "",
    initialDate: String = "",
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit,
    isDarkMode: Boolean
) {
    var taskTitle by remember { mutableStateOf(initialTitle) }
    var taskDesc by remember { mutableStateOf(initialDescription) }
    var taskDate by remember { mutableStateOf(initialDate) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val accentColor = MintGreen
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else DarkText

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
                }) { Text("OK", color = accentColor, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel", color = Color.Gray) }
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
                }) { Text("OK", color = accentColor, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel", color = Color.Gray) }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Black, color = textColor) },
        containerColor = cardBg,
        text = {
            Column {
                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = taskDesc,
                    onValueChange = { taskDesc = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = taskDate,
                    onValueChange = { taskDate = it },
                    label = { Text("Due Date") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.Event, null, tint = accentColor)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (taskTitle.isNotBlank()) onConfirm(taskTitle, taskDesc, taskDate) },
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save", color = DarkText, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}

@Composable
fun CartoonTaskItem(
    task: Task, 
    onDelete: () -> Unit, 
    onEdit: () -> Unit,
    cardBg: Color,
    borderColor: Color,
    textColor: Color,
    accentColor: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        color = cardBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(accentColor, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Event, null, tint = DarkText)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        task.title, 
                        color = textColor, 
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, null, tint = accentColor)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, null, tint = Color.Red.copy(alpha = 0.6f))
                    }
                }
            }
            
            if (task.description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    task.description,
                    color = textColor.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 60.dp)
                )
            }

            if (task.date.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 60.dp)
                ) {
                    Icon(Icons.Default.AccessTime, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(task.date, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
