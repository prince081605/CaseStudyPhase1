package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavController, isDarkMode: Boolean) {

    val tasks = listOf(
        "Capstone Proposal – Feb 5",
        "Database Quiz – Feb 8",
        "Mobile Dev Activity – Feb 10"
    )

    val blackBg = if (isDarkMode) Color(0xFF0F0F0F) else Color(0xFFF5F5F5)
    val cardBg = if (isDarkMode) Color(0xFF1A1A1A) else Color.White
    val innerCardBg = if (isDarkMode) Color(0xFF222222) else Color(0xFFF0F0F0)
    val cyan = Color(0xFF00BCD4)
    val textColor = if (isDarkMode) Color.White else Color.Black

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Academic Tasks", color = textColor) },
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
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.08f), 150f, Offset(size.width * 0.2f, size.height * 0.15f))
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.06f), 200f, Offset(size.width * 0.8f, size.height * 0.3f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(12.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Event, contentDescription = null, tint = cyan)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upcoming Tasks", style = MaterialTheme.typography.titleMedium, color = cyan)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        tasks.forEach { task ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = innerCardBg),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Event, contentDescription = null, tint = cyan)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(task, color = textColor)
                                }
                            }
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { },
                containerColor = cyan,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        }
    }
}
