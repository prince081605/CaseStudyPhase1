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
fun AnnouncementsScreen(navController: NavController) {

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
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF0B0B0B), Color(0xFF111111))
                        )
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Event, contentDescription = null, tint = cyan)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Enrollment starts next week", color = cyan, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Feb 3, 2026", color = grayText, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Students can start enrolling for the next semester starting Monday. Please check your account for available slots.",
                            color = white,
                            fontSize = 14.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = cyan)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("No classes on Feb 6", color = cyan, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Feb 1, 2026", color = grayText, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "All classes will be suspended on February 6 in observance of the holiday.",
                            color = white,
                            fontSize = 14.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Schedule, contentDescription = null, tint = cyan)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Library hours extended", color = cyan, style = MaterialTheme.typography.titleMedium)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Jan 30, 2026", color = grayText, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "The library will now be open until 9 PM for the next two weeks to accommodate students during finals.",
                            color = white,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
