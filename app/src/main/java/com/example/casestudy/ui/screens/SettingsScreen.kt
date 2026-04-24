package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.ui.theme.*
import com.example.casestudy.util.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var notificationsEnabled by remember { mutableStateOf(sessionManager.isNotificationsEnabled()) }

    // Cartoonish Theme Colors
    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else DarkText
    val accentColor = PastelPink
    val borderColor = if (isDarkMode) Color(0xFF333333) else DarkText.copy(alpha = 0.1f)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Settings", 
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
        containerColor = bgColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            CartoonSettingsCard(
                title = "Appearance",
                icon = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                color = cardBg,
                border = borderColor,
                accentColor = PastelBlue,
                textColor = textColor
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode", fontWeight = FontWeight.Bold, color = textColor)
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { onThemeChange(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = PastelBlue
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CartoonSettingsCard(
                title = "Notifications",
                icon = Icons.Default.Notifications,
                color = cardBg,
                border = borderColor,
                accentColor = MintGreen,
                textColor = textColor
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Enable Notifications", fontWeight = FontWeight.Bold, color = textColor)
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { 
                            notificationsEnabled = it
                            sessionManager.setNotificationsEnabled(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MintGreen
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CartoonSettingsCard(
                title = "Account",
                icon = Icons.Default.Person,
                color = cardBg,
                border = borderColor,
                accentColor = SoftPeach,
                textColor = textColor
            ) {
                Column {
                    Text("User: ${sessionManager.getUsername() ?: "User"}", color = textColor)
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = { }, contentPadding = PaddingValues(0.dp)) {
                        Text("Change Password", color = BubblegumPink, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CartoonSettingsCard(
                title = "App Info",
                icon = Icons.Default.Info,
                color = cardBg,
                border = borderColor,
                accentColor = SoftLavender,
                textColor = textColor
            ) {
                Column {
                    Text("Version 1.0.0", color = textColor.copy(alpha = 0.6f))
                    Text("Case Study Project", color = textColor.copy(alpha = 0.6f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    sessionManager.logout()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.7f))
            ) {
                Icon(Icons.Default.Logout, null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text("Logout", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CartoonSettingsCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    border: Color,
    accentColor: Color,
    textColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = border,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        color = color
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(accentColor, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, null, tint = DarkText, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = textColor
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}
