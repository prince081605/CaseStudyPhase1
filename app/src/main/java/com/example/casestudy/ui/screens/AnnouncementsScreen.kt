package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.casestudy.data.Announcement
import com.example.casestudy.ui.theme.BubblegumPink
import com.example.casestudy.ui.theme.DarkText
import com.example.casestudy.ui.theme.PastelBlue
import com.example.casestudy.ui.theme.PastelYellow
import com.example.casestudy.ui.viewmodel.AnnouncementViewModel
import com.example.casestudy.util.SessionManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(
    navController: NavController,
    viewModel: AnnouncementViewModel = viewModel()
) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val isAdmin = sessionManager.getUsername() == "admin"
    val isDarkMode = sessionManager.isDarkMode()

    val announcements by viewModel.allAnnouncements.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }

    // Cartoonish Theme Colors
    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val textColor = if (isDarkMode) Color.White else DarkText
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val accentColor = BubblegumPink
    val secondaryAccent = PastelBlue
    val borderColor = if (isDarkMode) Color(0xFF333333) else DarkText.copy(alpha = 0.1f)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Text(
                        "Announcements", 
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
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = accentColor,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Announcement")
                }
            }
        },
        containerColor = bgColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = accentColor)
                }
            } else if (announcements.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("No announcements yet", color = Color.Gray, fontWeight = FontWeight.Bold)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
                ) {
                    items(announcements) { announcement ->
                        CartoonAnnouncementItem(
                            announcement = announcement,
                            cardBg = cardBg,
                            borderColor = borderColor,
                            textColor = textColor,
                            accentColor = secondaryAccent
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            CartoonAddAnnouncementDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { title, content ->
                    val date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
                    viewModel.addAnnouncement(title, content, date)
                    showAddDialog = false
                },
                isDarkMode = isDarkMode
            )
        }
    }
}

@Composable
fun CartoonAnnouncementItem(
    announcement: Announcement,
    cardBg: Color,
    borderColor: Color,
    textColor: Color,
    accentColor: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        color = cardBg
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(accentColor, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.NotificationsActive, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = announcement.title,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp,
                        color = textColor
                    )
                    Text(
                        text = announcement.date,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = announcement.content,
                fontSize = 14.sp,
                color = textColor.copy(alpha = 0.8f),
                lineHeight = 20.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartoonAddAnnouncementDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String) -> Unit,
    isDarkMode: Boolean
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val accentColor = BubblegumPink
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkMode) Color.White else DarkText

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Post New Announcement", fontWeight = FontWeight.Black, color = textColor) },
        containerColor = cardBg,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("What's happening?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (title.isNotBlank() && content.isNotBlank()) onAdd(title, content) },
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Post", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}
