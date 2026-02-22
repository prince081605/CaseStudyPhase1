package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun SettingsScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {

    var notificationsEnabled by remember { mutableStateOf(true) }

    val blackBg = if (isDarkMode) Color(0xFF0F0F0F) else Color(0xFFF5F5F5)
    val cardBg = if (isDarkMode) Color(0xFF1A1A1A) else Color.White
    val cyan = Color(0xFF00BCD4)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val grayText = Color(0xFFAAAAAA)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", color = textColor) },
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
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.1f), 150f, Offset(size.width * 0.1f, size.height * 0.2f))
                drawCircle(Color(0xFF00BCD4).copy(alpha = 0.08f), 200f, Offset(size.width * 0.8f, size.height * 0.4f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                SettingsCard(title = "Account", icon = Icons.Default.Person, cyan = cyan, cardBg = cardBg) {
                    Column {
                        Text("Username: admin", color = textColor, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Change Password", color = cyan, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Notifications", icon = Icons.Default.Notifications, cyan = cyan, cardBg = cardBg) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Enable Notifications", color = textColor)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "On",
                                color = if (notificationsEnabled) cyan else grayText,
                                modifier = Modifier
                                    .clickable { notificationsEnabled = true }
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                fontSize = 16.sp
                            )
                            Text("|", color = grayText)
                            Text(
                                text = "Off",
                                color = if (!notificationsEnabled) cyan else grayText,
                                modifier = Modifier
                                    .clickable { notificationsEnabled = false }
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Appearance", icon = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode, cyan = cyan, cardBg = cardBg) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Dark Mode", color = textColor)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "On",
                                color = if (isDarkMode) cyan else grayText,
                                modifier = Modifier
                                    .clickable { onThemeChange(true) }
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                fontSize = 16.sp
                            )
                            Text("|", color = grayText)
                            Text(
                                text = "Off",
                                color = if (!isDarkMode) cyan else grayText,
                                modifier = Modifier
                                    .clickable { onThemeChange(false) }
                                    .padding(horizontal = 12.dp, vertical = 4.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Language", icon = Icons.Default.Language, cyan = cyan, cardBg = cardBg) {
                    Text("English", color = grayText)
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Privacy & Security", icon = Icons.Default.Lock, cyan = cyan, cardBg = cardBg) {
                    Column {
                        Text("Change Password", color = cyan)
                        Text("Two-Factor Authentication", color = cyan)
                        Text("App Permissions", color = cyan)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "Support & Help", icon = Icons.Default.Help, cyan = cyan, cardBg = cardBg) {
                    Column {
                        Text("FAQ", color = cyan)
                        Text("Contact Support", color = cyan)
                        Text("Terms & Conditions", color = cyan)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsCard(title = "App Info", icon = Icons.Default.Info, cyan = cyan, cardBg = cardBg) {
                    Text("Version 1.0.0", color = grayText)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SettingsCard(title: String, icon: ImageVector, cyan: Color, cardBg: Color, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
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
