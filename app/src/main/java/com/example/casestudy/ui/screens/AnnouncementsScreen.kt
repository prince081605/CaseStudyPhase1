package com.example.casestudy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = accentColor, strokeWidth = 6.dp)
                        Spacer(Modifier.height(16.dp))
                        Text("Fetching news...", color = textColor.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)
                    }
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
                        modifier = Modifier.size(80.dp),
                        tint = Color.Gray.copy(alpha = 0.4f)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text("No announcements yet", color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Stay tuned for updates!", color = Color.Gray.copy(alpha = 0.6f))
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

            // Global Error Message Overlay
            AnimatedVisibility(
                visible = errorMessage != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp)
            ) {
                errorMessage?.let { msg ->
                    Surface(
                        color = Color.Red.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = msg,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    LaunchedEffect(msg) {
                        kotlinx.coroutines.delay(3000)
                        errorMessage = null
                    }
                }
            }
        }

        if (showAddDialog) {
            CartoonAddAnnouncementDialog(
                onDismiss = { showAddDialog = false },
                onAdd = { title, content ->
                    if (title.isBlank() || content.isBlank()) {
                        errorMessage = "Please fill in all fields!"
                    } else {
                        val date = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
                        viewModel.addAnnouncement(title, content, date)
                        showAddDialog = false
                    }
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
                width = 3.dp,
                color = borderColor,
                shape = RoundedCornerShape(28.dp)
            ),
        shape = RoundedCornerShape(28.dp),
        color = cardBg,
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = accentColor
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.NotificationsActive, null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = announcement.title,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        color = textColor
                    )
                    Text(
                        text = announcement.date,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = announcement.content,
                fontSize = 15.sp,
                color = textColor.copy(alpha = 0.8f),
                lineHeight = 22.sp
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
    var showError by remember { mutableStateOf(false) }

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
                    onValueChange = { 
                        title = it
                        showError = false
                    },
                    label = { Text("Title", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { 
                        content = it
                        showError = false
                    },
                    label = { Text("What's happening?", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
                    )
                )
                if (showError) {
                    Text("Both fields are required!", color = Color.Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    if (title.isNotBlank() && content.isNotBlank()) {
                        onAdd(title, content)
                    } else {
                        showError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.height(50.dp).padding(horizontal = 8.dp)
            ) {
                Text("Post It! \uD83D\uDCE2", color = Color.White, fontWeight = FontWeight.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray, fontWeight = FontWeight.Bold)
            }
        },
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier.border(4.dp, accentColor.copy(alpha = 0.2f), RoundedCornerShape(32.dp))
    )
}
