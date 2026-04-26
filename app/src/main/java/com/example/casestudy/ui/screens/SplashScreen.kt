package com.example.casestudy.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.R
import com.example.casestudy.ui.theme.BubblegumPink
import com.example.casestudy.ui.theme.PastelYellow
import com.example.casestudy.util.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, isDarkMode: Boolean) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        delay(2000L) // 2 seconds delay
        navController.navigate("user_selection") {
            popUpTo("splash") { inclusive = true }
        }
    }

    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val textColor = if (isDarkMode) Color.White else Color(0xFF333333)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(scale.value)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Using your app icon/logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Smart Campus",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = textColor,
                letterSpacing = 2.sp
            )
            
            Text(
                text = "Your Ultimate School Buddy",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = textColor.copy(alpha = 0.6f)
            )
        }
    }
}
