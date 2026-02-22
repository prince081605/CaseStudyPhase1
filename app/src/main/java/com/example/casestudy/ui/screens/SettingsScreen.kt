package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    val blackBg = Color(0xFF0F0F0F)
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val grayText = Color(0xFFAAAAAA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = white) },
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
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.1f), 150f, Offset(size.width * 0.1f, size.height * 0.2f))
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.08f), 200f, Offset(size.width * 0.8f, size.height * 0.4f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                SettingsCard(title = "Account", icon = Icons.Default.Person, cyan = cyan, darkCard = darkCard) {
                    Column {
                        Text("Username: admin", color = white, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Change Password", color = cyan, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Notifications", icon = Icons.Default.Notifications, cyan = cyan, darkCard = darkCard) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Enable Notifications", color = white)
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = cyan, checkedTrackColor = cyan.copy(alpha = 0.3f))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Appearance", icon = Icons.Default.DarkMode, cyan = cyan, darkCard = darkCard) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Dark Mode", color = white)
                        Switch(
                            checked = darkModeEnabled,
                            onCheckedChange = { darkModeEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = cyan, checkedTrackColor = cyan.copy(alpha = 0.3f))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Language", icon = Icons.Default.Language, cyan = cyan, darkCard = darkCard) {
                    Text("English", color = grayText)
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Privacy & Security", icon = Icons.Default.Lock, cyan = cyan, darkCard = darkCard) {
                    Column {
                        Text("Change Password", color = cyan)
                        Text("Two-Factor Authentication", color = cyan)
                        Text("App Permissions", color = cyan)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Support & Help", icon = Icons.Default.Help, cyan = cyan, darkCard = darkCard) {
                    Column {
                        Text("FAQ", color = cyan)
                        Text("Contact Support", color = cyan)
                        Text("Terms & Conditions", color = cyan)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "App Info", icon = Icons.Default.Info, cyan = cyan, darkCard = darkCard) {
                    Text("Version 1.0.0", color = grayText)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SettingsCard(title: String, icon: ImageVector, cyan: Color, darkCard: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = darkCard),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = cyan, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(title, color = cyan, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}
