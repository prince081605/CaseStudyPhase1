package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.casestudy.util.SessionManager

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val cyan = Color(0xFF00BCD4)
    val dark1 = Color(0xFF0F0F0F)
    val dark2 = Color(0xFF121212)
    val dark3 = Color(0xFF1A1A1A)
    val white = Color.White

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(dark1, dark2, dark3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {

        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopStart)
                .offset(x = (-80).dp, y = (-80).dp)
                .background(cyan.copy(alpha = 0.25f), CircleShape)
                .blur(120.dp)
        )

        Box(
            modifier = Modifier
                .size(260.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 80.dp, y = 80.dp)
                .background(cyan.copy(alpha = 0.2f), CircleShape)
                .blur(120.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                elevation = CardDefaults.cardElevation(12.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Smart CC",
                        style = MaterialTheme.typography.headlineMedium,
                        color = cyan
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username", color = white) },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = cyan,
                            unfocusedBorderColor = white,
                            focusedTextColor = white,
                            unfocusedTextColor = white,
                            cursorColor = cyan
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = white) },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null,
                                tint = cyan,
                                modifier = Modifier.clickable {
                                    passwordVisible = !passwordVisible
                                }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = cyan,
                            unfocusedBorderColor = white,
                            focusedTextColor = white,
                            unfocusedTextColor = white,
                            cursorColor = cyan
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (username == "admin" && password == "1234") {
                                sessionManager.saveLogin(username)
                                navController.navigate("dashboard")
                            } else {
                                error = "Invalid credentials"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cyan,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Forgot Password?",
                        color = cyan,
                        modifier = Modifier.clickable { }
                    )
                }
            }
        }
    }
}
