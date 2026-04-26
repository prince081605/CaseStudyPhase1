package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.ui.theme.*

@Composable
fun UserSelectionScreen(navController: NavController, isDarkMode: Boolean) {
    // Cartoonish Theme Colors (Matching LoginScreen)
    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val accentColor = BubblegumPink
    val textColor = if (isDarkMode) Color.White else DarkText
    val borderColor = if (isDarkMode) Color.White.copy(alpha = 0.2f) else DarkText.copy(alpha = 0.2f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        // Playful background decoration
        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .background(if (isDarkMode) MintGreen.copy(alpha = 0.3f) else MintGreen, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .background(if (isDarkMode) PastelBlue.copy(alpha = 0.3f) else PastelBlue, CircleShape)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .border(width = 4.dp, color = textColor.copy(alpha = 0.1f), shape = RoundedCornerShape(32.dp)),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = cardBg),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome! \uD83D\uDE80",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = textColor,
                    fontSize = 32.sp
                )
                
                Text(
                    text = "Smart Campus Hub",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = textColor.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(40.dp))

                UserTypeButton(
                    title = "Student",
                    icon = Icons.Default.School,
                    onClick = { navController.navigate("login/student") },
                    accentColor = accentColor,
                    textColor = textColor,
                    isDarkMode = isDarkMode
                )

                Spacer(modifier = Modifier.height(20.dp))

                UserTypeButton(
                    title = "Admin",
                    icon = Icons.Default.AdminPanelSettings,
                    onClick = { navController.navigate("login/admin") },
                    accentColor = accentColor,
                    textColor = textColor,
                    isDarkMode = isDarkMode
                )
            }
        }
    }
}

@Composable
fun UserTypeButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    accentColor: Color,
    textColor: Color,
    isDarkMode: Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDarkMode) Color.White.copy(alpha = 0.1f) else Color.White,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, accentColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
