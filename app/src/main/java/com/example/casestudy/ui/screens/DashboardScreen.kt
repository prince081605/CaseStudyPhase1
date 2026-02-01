package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.casestudy.util.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername() ?: "User"

    // Theme Colors
    val blackBg = Color(0xFF0F0F0F)
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Dashboard", color = white) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = white,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = blackBg)
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(blackBg),
            contentAlignment = Alignment.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = darkCard),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Welcome, $username",
                        style = MaterialTheme.typography.headlineSmall,
                        color = cyan
                    )

                    Text(
                        text = "Smart Campus Companion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = white
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Buttons
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                        onClick = { navController.navigate("campus") }
                    ) { Text("Campus Information") }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                        onClick = { navController.navigate("tasks") }
                    ) { Text("Academic Tasks & Schedule") }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                        onClick = { navController.navigate("announcements") }
                    ) { Text("Campus Announcements") }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                        onClick = { navController.navigate("settings") }
                    ) { Text("Settings") }

                    Spacer(modifier = Modifier.height(24.dp))

                    Divider(color = Color.Gray)

                    Spacer(modifier = Modifier.height(16.dp))

                    // ✅ Fixed Logout Button
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                        onClick = {
                            sessionManager.logout()
                            navController.navigate("login")
                        }
                    ) {
                        Text("Logout", color = cyan)
                    }

                }
            }
        }
    }
}
