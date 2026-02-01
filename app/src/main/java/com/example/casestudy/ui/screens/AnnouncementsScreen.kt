package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(navController: NavController) {

    val announcements = listOf(
        "Enrollment starts next week",
        "No classes on Feb 6",
        "Library hours extended"
    )

    // Theme Colors
    val blackBg = Color(0xFF0F0F0F)
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Announcements", color = white) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = white
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blackBg)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(blackBg)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = darkCard),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column(modifier = Modifier.padding(24.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Announcement,
                            contentDescription = null,
                            tint = cyan
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Campus Announcements",
                            style = MaterialTheme.typography.titleMedium,
                            color = cyan
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    announcements.forEach {
                        Text("• $it", color = white)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }
    }
}
