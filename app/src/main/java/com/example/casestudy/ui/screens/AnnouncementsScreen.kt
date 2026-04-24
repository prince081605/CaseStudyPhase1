package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.casestudy.data.Announcement
import com.example.casestudy.ui.viewmodel.AnnouncementViewModel
import com.example.casestudy.util.SessionManager
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(navController: NavController, viewModel: AnnouncementViewModel = viewModel()) {

    val announcements by viewModel.allAnnouncements.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val isAdmin = sessionManager.getUsername() == "admin"

    var showAddDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val blackBg = Color(0xFF0F0F0F)
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val grayText = Color(0xFFAAAAAA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements", color = white) },
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
            if (isAdmin) {
                FloatingActionButton(onClick = { showAddDialog = true }, containerColor = cyan) {
                    Icon(Icons.Default.Add, contentDescription = "Post Announcement", tint = Color.Black)
                }
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
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.08f), 150f, Offset(size.width * 0.15f, size.height * 0.2f))
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.06f), 200f, Offset(size.width * 0.8f, size.height * 0.35f))
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = cyan)
                }
            } else if (announcements.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No announcements yet.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(announcements) { announcement ->
                        AnnouncementItem(
                            announcement = announcement,
                            onRead = { viewModel.markAsRead(announcement) },
                            cyan = cyan,
                            white = white,
                            grayText = grayText,
                            darkCard = darkCard
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("New Announcement", color = white) },
            containerColor = darkCard,
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title", color = white) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = white,
                            unfocusedTextColor = white,
                            focusedBorderColor = cyan,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Content", color = white) },
                        modifier = Modifier.height(100.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = white,
                            unfocusedTextColor = white,
                            focusedBorderColor = cyan,
                            unfocusedBorderColor = Color.Gray
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
                            viewModel.addAnnouncement(title, content, currentDate)
                            title = ""
                            content = ""
                            showAddDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = cyan)
                ) {
                    Text("Post", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel", color = cyan)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementItem(
    announcement: Announcement,
    onRead: () -> Unit,
    cyan: Color,
    white: Color,
    grayText: Color,
    darkCard: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = if (announcement.isRead) darkCard.copy(alpha = 0.5f) else darkCard),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onRead
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (announcement.isRead) Icons.Default.Drafts else Icons.Default.MarkEmailUnread,
                    contentDescription = null,
                    tint = if (announcement.isRead) grayText else cyan
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = announcement.title,
                    color = if (announcement.isRead) grayText else cyan,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("${announcement.date} • ${announcement.author}", color = grayText, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = announcement.content,
                color = if (announcement.isRead) grayText else white,
                fontSize = 14.sp
            )
        }
    }
}
