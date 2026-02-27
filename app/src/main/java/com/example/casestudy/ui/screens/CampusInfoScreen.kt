package com.example.casestudy.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusInfoScreen(navController: NavController) {

    val departments = listOf(
        "College of Computing Studies",
        "College of Engineering",
        "College of Education",
        "College of Business and Accountancy",
        "College of Arts and Sciences"
    )

    // Theme-like colors (pwede mo ilipat sa Theme.kt later)
    val blackBg = Color(0xFF0F0F0F)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val darkCard = Color(0xFF1A1A1A)
    val chipBg = Color(0xFF222222)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Information", color = white) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = white
                        )
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

            // Gradient background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF0B0B0B), Color(0xFF111111))
                        )
                    )
            )

            // Glow circles
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = cyan.copy(alpha = 0.10f),
                    radius = 150f,
                    center = Offset(size.width * 0.2f, size.height * 0.1f)
                )
                drawCircle(
                    color = cyan.copy(alpha = 0.08f),
                    radius = 200f,
                    center = Offset(size.width * 0.8f, size.height * 0.3f)
                )
                drawCircle(
                    color = cyan.copy(alpha = 0.05f),
                    radius = 250f,
                    center = Offset(size.width * 0.5f, size.height * 0.7f)
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AcademicCollegesCard(
                    departments = departments,
                    darkCard = darkCard,
                    cyan = cyan,
                    white = white,
                    chipBg = chipBg
                )

                ContactInfoCard(
                    darkCard = darkCard,
                    cyan = cyan,
                    white = white
                )

                AdminOfficeHoursCard(
                    darkCard = darkCard,
                    cyan = cyan,
                    white = white
                )

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                    onClick = { navController.popBackStack() }
                ) {
                    Text("Back")
                }
            }
        }
    }
}

@Composable
private fun AcademicCollegesCard(
    departments: List<String>,
    darkCard: Color,
    cyan: Color,
    white: Color,
    chipBg: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = darkCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            SectionHeader(
                icon = Icons.Default.School,
                title = "Academic Colleges",
                cyan = cyan
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Replaced FlowRow with LazyRow (no dependency issues)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(end = 8.dp)
            ) {
                items(departments) { dept ->
                    AssistChip(
                        onClick = { },
                        label = { Text(dept, color = white, fontSize = 14.sp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = chipBg,
                            labelColor = white
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactInfoCard(darkCard: Color, cyan: Color, white: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = darkCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            SectionHeader(
                icon = Icons.Default.ContactMail,
                title = "Campus Contact Information",
                cyan = cyan
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow(Icons.Default.Email, "info@pnc.edu.ph", cyan, white)
            InfoRow(Icons.Default.Phone, "(049) 545-5453", cyan, white)
            InfoRow(Icons.Default.LocationOn, "Barangay Banay-Banay, Cabuyao City, Laguna", cyan, white)
        }
    }
}

@Composable
private fun AdminOfficeHoursCard(darkCard: Color, cyan: Color, white: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = darkCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            SectionHeader(
                icon = Icons.Default.Schedule,
                title = "Administrative Office Hours",
                cyan = cyan
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Monday to Friday", color = white, style = MaterialTheme.typography.bodyLarge)
            Text("8:00 AM – 5:00 PM", color = white, style = MaterialTheme.typography.bodyLarge)

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
private fun SectionHeader(icon: ImageVector, title: String, cyan: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = cyan,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            color = cyan,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    text: String,
    iconColor: Color,
    textColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = textColor, style = MaterialTheme.typography.bodyLarge)
    }
}
