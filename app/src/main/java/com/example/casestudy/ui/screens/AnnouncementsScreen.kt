package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(navController: NavController, isDarkMode: Boolean) {

    val blackBg = if (isDarkMode) Color(0xFF0F0F0F) else Color(0xFFF5F5F5)
    val cardBg = if (isDarkMode) Color(0xFF1A1A1A) else Color.White
    val cyan = Color(0xFF00BCD4)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val grayText = Color(0xFFAAAAAA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements", color = textColor) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textColor)
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
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isDarkMode) {
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF0B0B0B), Color(0xFF111111))
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFF5F5F5), Color(0xFFE0E0E0))
                            )
                        }
                    )
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.08f), 150f, Offset(size.width * 0.15f, size.height * 0.2f))
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.06f), 200f, Offset(size.width * 0.8f, size.height * 0.35f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnnouncementCard(
                    icon = Icons.Default.Event,
                    title = "Enrollment starts next week",
                    date = "Feb 3, 2026",
                    description = "Students can start enrolling for the next semester starting Monday. Please check your account for available slots.",
                    cardBg = cardBg,
                    cyan = cyan,
                    textColor = textColor,
                    grayText = grayText
                )

                AnnouncementCard(
                    icon = Icons.Default.Info,
                    title = "No classes on Feb 6",
                    date = "Feb 1, 2026",
                    description = "All classes will be suspended on February 6 in observance of the holiday.",
                    cardBg = cardBg,
                    cyan = cyan,
                    textColor = textColor,
                    grayText = grayText
                )

                AnnouncementCard(
                    icon = Icons.Default.Schedule,
                    title = "Library hours extended",
                    date = "Jan 30, 2026",
                    description = "The library will now be open until 9 PM for the next two weeks to accommodate students during finals.",
                    cardBg = cardBg,
                    cyan = cyan,
                    textColor = textColor,
                    grayText = grayText
                )
            }
        }
    }
}

@Composable
fun AnnouncementCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    date: String,
    description: String,
    cardBg: Color,
    cyan: Color,
    textColor: Color,
    grayText: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = cyan)
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, color = cyan, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(date, color = grayText, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                description,
                color = textColor,
                fontSize = 14.sp
            )
        }
    }
}
