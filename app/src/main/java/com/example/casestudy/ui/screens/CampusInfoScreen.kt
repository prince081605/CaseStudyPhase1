package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    val blackBg = Color(0xFF0F0F0F)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val darkCard = Color(0xFF1A1A1A)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Campus Information",
                        color = white
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = white
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = blackBg
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(blackBg)
                .padding(16.dp)
        ) {

            // Departments Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = darkCard),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.School, contentDescription = null, tint = cyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Academic Colleges", style = MaterialTheme.typography.titleMedium, color = cyan)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        departments.forEach {
                            AssistChip(
                                onClick = {},
                                label = { Text(it, color = white) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = blackBg,
                                    labelColor = white
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Information Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = darkCard),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ContactMail, contentDescription = null, tint = cyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Campus Contact Information", style = MaterialTheme.typography.titleMedium, color = cyan)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow(Icons.Default.Email, "info@pnc.edu.ph", cyan, white)
                    InfoRow(Icons.Default.Phone, "(049) 545-5453", cyan, white)
                    InfoRow(Icons.Default.LocationOn, "Barangay Banay-Banay, Cabuyao City, Laguna", cyan, white)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Administrative Office Hours Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = darkCard),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = cyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Administrative Office Hours", style = MaterialTheme.typography.titleMedium, color = cyan)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Monday to Friday", color = white)
                    Text("8:00 AM – 5:00 PM", color = white)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Closed during weekends and official holidays",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    iconColor: Color,
    textColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = textColor)
    }
    Spacer(modifier = Modifier.height(6.dp))
}
