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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusInfoScreen(navController: NavController, isDarkMode: Boolean) {

    val departments = listOf(
        "College of Computing Studies",
        "College of Engineering",
        "College of Education",
        "College of Business and Accountancy",
        "College of Arts and Sciences"
    )

    val blackBg = if (isDarkMode) Color(0xFF0F0F0F) else Color(0xFFF5F5F5)
    val cardBg = if (isDarkMode) Color(0xFF1A1A1A) else Color.White
    val cyan = Color(0xFF00BCD4)
    val textColor = if (isDarkMode) Color.White else Color.Black

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Information", color = textColor) },
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
                drawCircle(
                    color = Color(0xFF00BCD4).copy(alpha = 0.1f),
                    radius = 150f,
                    center = Offset(size.width * 0.2f, size.height * 0.1f)
                )
                drawCircle(
                    color = Color(0xFF00BCD4).copy(alpha = 0.08f),
                    radius = 200f,
                    center = Offset(size.width * 0.8f, size.height * 0.3f)
                )
                drawCircle(
                    color = Color(0xFF00BCD4).copy(alpha = 0.05f),
                    radius = 250f,
                    center = Offset(size.width * 0.5f, size.height * 0.7f)
                )
            }

            // Main content scrollable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Cards (same as previous improved design)
                AcademicCollegesCard(departments, cardBg, cyan, textColor)
                Spacer(modifier = Modifier.height(20.dp))
                ContactInfoCard(cardBg, cyan, textColor)
                Spacer(modifier = Modifier.height(20.dp))
                AdminOfficeHoursCard(cardBg, cyan, textColor)
            }
        }
    }
}

// Separated composables for cleaner code
@Composable
fun AcademicCollegesCard(departments: List<String>, cardBg: Color, cyan: Color, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.School, contentDescription = null, tint = cyan, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Academic Colleges", color = cyan, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                departments.forEach {
                    AssistChip(
                        onClick = { },
                        label = { Text(it, color = textColor, fontSize = 14.sp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (textColor == Color.White) Color(0xFF222222) else Color(0xFFEEEEEE),
                            labelColor = textColor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ContactInfoCard(cardBg: Color, cyan: Color, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ContactMail, contentDescription = null, tint = cyan, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Campus Contact Information", color = cyan, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            InfoRow(Icons.Default.Email, "info@pnc.edu.ph", cyan, textColor)
            InfoRow(Icons.Default.Phone, "(049) 545-5453", cyan, textColor)
            InfoRow(Icons.Default.LocationOn, "Barangay Banay-Banay, Cabuyao City, Laguna", cyan, textColor)
        }
    }
}

@Composable
fun AdminOfficeHoursCard(cardBg: Color, cyan: Color, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = cyan, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Administrative Office Hours", color = cyan, style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Monday to Friday", color = textColor, style = MaterialTheme.typography.bodyLarge)
            Text("8:00 AM – 5:00 PM", color = textColor, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Closed during weekends and official holidays",
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, iconColor: Color, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = textColor, style = MaterialTheme.typography.bodyLarge)
    }
}
