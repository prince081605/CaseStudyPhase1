package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.ui.theme.*
import com.example.casestudy.util.SessionManager

@Composable
fun LoginScreen(navController: NavController, isDarkMode: Boolean) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Cartoonish Theme Colors
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
        // Playful background decoration (visible even in dark mode but more subtle)
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
                    text = "Welcome! \uD83D\uDC4B",
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

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", fontWeight = FontWeight.Bold) },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = borderColor,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.5f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", fontWeight = FontWeight.Bold) },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null,
                                tint = textColor.copy(alpha = 0.5f)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = borderColor,
                        focusedLabelColor = accentColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.5f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    )
                )

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = error,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if ((username == "admin" && password == "1234") || 
                            (username == "student" && password == "1234")) {
                            sessionManager.saveLogin(username)
                            navController.navigate("dashboard") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            error = "Oops! Try admin/1234 or student/1234"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Let's Go!",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Forgot Password?",
                    color = textColor.copy(alpha = 0.4f),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}
