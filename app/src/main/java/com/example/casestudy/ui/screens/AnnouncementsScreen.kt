package com.example.casestudy.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.data.Announcement
import com.example.casestudy.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(navController: NavController, viewModel: MainViewModel) {
    val announcements by viewModel.announcements.collectAsState()

    val blackBg = Color(0xFF0F0F0F)
    val white = Color.White

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
        containerColor = blackBg
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(announcements) { announcement ->
                    AnnouncementItem(
                        announcement = announcement,
                        onMarkAsRead = {
                            viewModel.updateAnnouncement(announcement.copy(isRead = true))
                        },
                        onMarkAsUnread = {
                            viewModel.updateAnnouncement(announcement.copy(isRead = false))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementItem(
    announcement: Announcement,
    onMarkAsRead: () -> Unit,
    onMarkAsUnread: () -> Unit
) {
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val grayText = Color(0xFFAAAAAA)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { /* Could navigate to detail if needed */ },
                onLongClick = onMarkAsUnread
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = darkCard),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = if (announcement.isRead) Color.Gray else cyan
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = announcement.title,
                    color = if (announcement.isRead) Color.Gray else cyan,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = announcement.date, color = grayText, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = announcement.content,
                color = white,
                fontSize = 14.sp
            )
            if (!announcement.isRead) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = onMarkAsRead,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Mark as Read", color = cyan)
                }
            }
        }
    }
}
