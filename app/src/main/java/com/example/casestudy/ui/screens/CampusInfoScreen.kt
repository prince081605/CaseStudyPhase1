package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.casestudy.R
import com.example.casestudy.ui.theme.*
import com.example.casestudy.util.SessionManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CampusInfoScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val isDarkMode = sessionManager.isDarkMode()

    val departments = listOf(
        "College of Computing Studies" to R.drawable.ccs,
        "College of Engineering" to R.drawable.coe,
        "College of Education" to R.drawable.coed,
        "College of Business and Accountancy" to R.drawable.cbaa,
        "College of Arts and Sciences" to R.drawable.cas,
        "College of Health and Allied Sciences" to R.drawable.chas
    )

    // Cartoonish Theme Colors
    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else DarkText
    val accentColor = PastelBlue
    val secondaryAccent = SoftLavender
    val borderColor = if (isDarkMode) Color(0xFF333333) else DarkText.copy(alpha = 0.1f)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Campus Info", 
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
            // Academic Colleges Card
            CartoonInfoCard(
                title = "Academic Colleges",
                icon = Icons.Default.School,
                color = cardBg,
                border = borderColor,
                accentColor = accentColor,
                textColor = textColor
            ) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    departments.forEach { dept ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.3f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = dept.first,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = textColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Contact Info Card
            CartoonInfoCard(
                title = "Contact Details",
                icon = Icons.Default.ContactMail,
                color = cardBg,
                border = borderColor,
                accentColor = MintGreen,
                textColor = textColor
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ContactRow(Icons.Default.Email, "info@pnc.edu.ph", textColor)
                    ContactRow(Icons.Default.Phone, "(049) 545-5453", textColor)
                    ContactRow(Icons.Default.LocationOn, "Barangay Banay-Banay, Cabuyao City, Laguna", textColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Office Hours Card
            CartoonInfoCard(
                title = "Office Hours",
                icon = Icons.Default.Schedule,
                color = cardBg,
                border = borderColor,
                accentColor = secondaryAccent,
                textColor = textColor
            ) {
                Column {
                    Text(
                        "Monday to Friday", 
                        fontWeight = FontWeight.Bold, 
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "8:00 AM – 5:00 PM", 
                        color = textColor.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Closed on weekends and holidays",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun CartoonInfoCard(
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
        Column(modifier = Modifier.padding(24.dp)) {
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

@Composable
fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, color = textColor, fontSize = 14.sp)
    }
}
