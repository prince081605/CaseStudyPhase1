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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

    // Custom colors
    val blackBg = Color(0xFF0F0F0F)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blackBg),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1A1A)
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Smart CC",
                    style = MaterialTheme.typography.headlineSmall,
                    color = cyan
                )

                Spacer(modifier = Modifier.height(12.dp))


                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = white) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = cyan,
                        unfocusedBorderColor = white,
                        focusedTextColor = white,
                        unfocusedTextColor = white,
                        cursorColor = cyan
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = white) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
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
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cyan,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        if (username == "admin" && password == "1234") {
                            sessionManager.saveLogin(username)
                            navController.navigate("dashboard")
                        } else {
                            error = "Invalid credentials"
                        }
                    }
                ) {
                    Text("Login")
                }
            }
        }
    }
}
