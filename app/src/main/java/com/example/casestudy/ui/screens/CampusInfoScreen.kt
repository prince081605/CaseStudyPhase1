package com.example.casestudy.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampusInfoScreen(navController: NavController) {

    val departments = listOf(
        "College of Computing Studies" to R.drawable.ccs,
        "College of Engineering" to R.drawable.coe,
        "College of Education" to R.drawable.coed,
        "College of Business and Accountancy" to R.drawable.cbaa,
        "College of Arts and Sciences" to R.drawable.cas,
        "College of Health and Allied Sciences" to R.drawable.chas
    )

    // Dynamic colors
    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    val textColor = MaterialTheme.colorScheme.onBackground
    val primaryColor = MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Information", color = textColor) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = textColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                AcademicCollegesCard(departments, cardColor, primaryColor, textColor)
                Spacer(modifier = Modifier.height(20.dp))
                ContactInfoCard(cardColor, primaryColor, textColor)
                Spacer(modifier = Modifier.height(20.dp))
                AdminOfficeHoursCard(cardColor, primaryColor, textColor)
            }
        }
    }
}

@Composable
fun AcademicCollegesCard(
    departments: List<Pair<String, Int>>,
    cardColor: Color,
    primaryColor: Color,
    textColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Academic Colleges",
                    color = primaryColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            departments.forEachIndexed { index, department ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = department.second),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = department.first,
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f),
                        fontSize = 15.sp
                    )
                }

                if (index < departments.size - 1) {
                    HorizontalDivider(
                        color = textColor.copy(alpha = 0.1f),
                        modifier = Modifier.padding(start = 66.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactInfoCard(cardColor: Color, primaryColor: Color, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ContactMail, contentDescription = null, tint = primaryColor, modifier = Modifier.size(26.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Campus Contact Information", color = primaryColor, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            InfoRow(Icons.Default.Email, "info@pnc.edu.ph", primaryColor, textColor)
            InfoRow(Icons.Default.Phone, "(049) 545-5453", primaryColor, textColor)
            InfoRow(Icons.Default.LocationOn, "Barangay Banay-Banay, Cabuyao City, Laguna", primaryColor, textColor)
        }
    }
}

@Composable
fun AdminOfficeHoursCard(cardColor: Color, primaryColor: Color, textColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Schedule, contentDescription = null, tint = primaryColor, modifier = Modifier.size(26.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Administrative Office Hours", color = primaryColor, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Monday to Friday", color = textColor, style = MaterialTheme.typography.bodyLarge)
            Text("8:00 AM – 5:00 PM", color = textColor, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Closed during weekends and official holidays",
                color = textColor.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, iconColor: Color, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = textColor, style = MaterialTheme.typography.bodyLarge, fontSize = 15.sp)
    }
}
