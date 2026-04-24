package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.casestudy.ui.theme.*
import com.example.casestudy.ui.viewmodel.AnnouncementViewModel
import com.example.casestudy.ui.viewmodel.TaskViewModel
import com.example.casestudy.util.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController, 
    isDarkMode: Boolean,
    taskViewModel: TaskViewModel = viewModel(),
    announcementViewModel: AnnouncementViewModel = viewModel()
) {
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername() ?: "User"

    val tasks by taskViewModel.allTasks.collectAsState()
    val isTasksLoading by taskViewModel.isLoading.collectAsState()
    val announcements by announcementViewModel.allAnnouncements.collectAsState()
    val isAnnouncementsLoading by announcementViewModel.isLoading.collectAsState()

    val pendingTasksCount = tasks.count { !it.isCompleted }
    val latestAnnouncement = announcements.firstOrNull()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.7f

    // Notification Logic
    var showNotificationDialog by remember { mutableStateOf(false) }
    var notificationMessage by remember { mutableStateOf("") }

    LaunchedEffect(announcements) {
        if (announcements.isNotEmpty()) {
            val latestId = announcements.first().id
            val lastSeenId = sessionManager.getLastSeenAnnouncementId()
            
            // If the latest announcement is different from the last one seen, and notifications are enabled
            if (latestId != lastSeenId && sessionManager.isNotificationsEnabled() && username != "admin") {
                notificationMessage = announcements.first().title
                showNotificationDialog = true
                sessionManager.saveLastSeenAnnouncementId(latestId)
            }
        }
    }

    // Cartoonish Theme Colors
    val bgColor = if (isDarkMode) Color(0xFF121212) else PastelYellow
    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val accentColor = BubblegumPink
    val secondaryAccent = PastelBlue
    val textColor = if (isDarkMode) Color.White else DarkText
    val borderColor = if (isDarkMode) Color(0xFF333333) else DarkText.copy(alpha = 0.2f)
    
    val pendingCardBg = if (isDarkMode) MintGreen.copy(alpha = 0.2f) else MintGreen
    val newsCardBg = if (isDarkMode) SoftLavender.copy(alpha = 0.2f) else SoftLavender

    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            title = { 
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Campaign, null, tint = accentColor, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("New Announcement!", fontWeight = FontWeight.Black, color = textColor)
                }
            },
            text = { 
                Text(
                    "Admin posted a new update: \"$notificationMessage\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                ) 
            },
            containerColor = cardBg,
            confirmButton = {
                Button(
                    onClick = { 
                        showNotificationDialog = false
                        navController.navigate("announcements")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Now", color = DarkText, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNotificationDialog = false }) {
                    Text("Later", color = Color.Gray)
                }
            },
            modifier = Modifier.border(2.dp, accentColor, RoundedCornerShape(28.dp))
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(drawerWidth),
                drawerContainerColor = if (isDarkMode) Color(0xFF1A1A1A) else PastelPink,
                windowInsets = WindowInsets(0)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(accentColor)
                        .padding(vertical = 40.dp, horizontal = 16.dp)
                ) {
                    Column {
                        Icon(
                            Icons.Default.Face,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Hello, $username!",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                val menuItems = listOf(
                    Triple("Dashboard", Icons.Default.Home, "dashboard"),
                    Triple("Campus Info", Icons.Default.School, "campus"),
                    Triple("My Tasks", Icons.Default.Event, "tasks"),
                    Triple("Announcements", Icons.Default.Campaign, "announcements"),
                    Triple("Settings", Icons.Default.Settings, "settings")
                )

                menuItems.forEach { (label, icon, route) ->
                    NavigationDrawerItem(
                        label = { Text(label, fontWeight = FontWeight.Medium, color = textColor) },
                        selected = false,
                        icon = { Icon(icon, null, tint = if (isDarkMode) secondaryAccent else DarkText) },
                        onClick = { 
                            if (route == "dashboard") scope.launch { drawerState.close() }
                            else navController.navigate(route) 
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        )
                    )
                }

                Spacer(Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Logout", color = Color.Red, fontWeight = FontWeight.Bold) },
                    selected = false,
                    icon = { Icon(Icons.Default.Logout, null, tint = Color.Red) },
                    onClick = {
                        sessionManager.logout()
                        navController.navigate("login")
                    },
                    modifier = Modifier.padding(12.dp),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            "Smart Campus", 
                            fontWeight = FontWeight.ExtraBold,
                            color = textColor,
                            letterSpacing = 1.sp
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, null, tint = textColor, modifier = Modifier.size(28.dp))
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = bgColor
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Feature: Quick Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CartoonCard(
                        modifier = Modifier.weight(1f),
                        color = pendingCardBg,
                        border = if (isDarkMode) MintGreen else Color.Transparent,
                        onClick = { navController.navigate("tasks") }
                    ) {
                        Column {
                            Text("Pending", style = MaterialTheme.typography.labelLarge, color = textColor)
                            Text(
                                text = if (isTasksLoading) "..." else "$pendingTasksCount",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                color = textColor
                            )
                        }
                    }
                    CartoonCard(
                        modifier = Modifier.weight(1f),
                        color = newsCardBg,
                        border = if (isDarkMode) SoftLavender else Color.Transparent,
                        onClick = { navController.navigate("announcements") }
                    ) {
                        Column {
                            Text("News", style = MaterialTheme.typography.labelLarge, color = textColor)
                            Text(
                                text = if (isAnnouncementsLoading) "..." else "${announcements.size}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Black,
                                color = textColor
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    "Latest Update",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )

                // Large Announcement Card
                CartoonCard(
                    modifier = Modifier.fillMaxWidth(),
                    color = cardBg,
                    border = borderColor,
                    onClick = { navController.navigate("announcements") }
                ) {
                    Column(Modifier.padding(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(secondaryAccent, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.NotificationsActive, null, tint = Color.White)
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    latestAnnouncement?.title ?: "Welcome!",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = textColor
                                )
                                Text(
                                    latestAnnouncement?.date ?: "Today",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isDarkMode) Color.LightGray else Color.Gray
                                )
                            }
                        }
                        
                        Spacer(Modifier.height(12.dp))
                        
                        Text(
                            text = latestAnnouncement?.content ?: "Explore your campus and manage your tasks effectively.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(alpha = 0.8f),
                            maxLines = 3
                        )
                        
                        Spacer(Modifier.height(12.dp))
                        
                        Button(
                            onClick = { navController.navigate("announcements") },
                            colors = ButtonDefaults.buttonColors(containerColor = secondaryAccent),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("Read More", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Bottom Menu Grid
                Text(
                    "Quick Access",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    MenuIconBox(
                        label = "Campus", 
                        icon = Icons.Default.Map, 
                        color = SoftPeach,
                        textColor = textColor,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("campus") }
                    )
                    MenuIconBox(
                        label = "Settings", 
                        icon = Icons.Default.Settings, 
                        color = PastelPink,
                        textColor = textColor,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("settings") }
                    )
                    MenuIconBox(
                        label = "Tasks", 
                        icon = Icons.Default.Assignment, 
                        color = MintGreen,
                        textColor = textColor,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("tasks") }
                    )
                }
            }
        }
    }
}

@Composable
fun CartoonCard(
    modifier: Modifier = Modifier,
    color: Color,
    border: Color = Color.Transparent,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = if (border != Color.Transparent) 2.dp else 0.dp,
                color = border,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        color = color,
        shadowElevation = 0.dp
    ) {
        Box(Modifier.padding(20.dp)) {
            content()
        }
    }
}

@Composable
fun RowScope.MenuIconBox(
    label: String, 
    icon: androidx.compose.ui.graphics.vector.ImageVector, 
    color: Color, 
    textColor: Color,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .background(if (isDarkMode) color.copy(alpha = 0.2f) else color, RoundedCornerShape(20.dp))
                .border(if (isDarkMode) 2.dp else 0.dp, color, RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = if (isDarkMode) color else DarkText, modifier = Modifier.size(28.dp))
        }
        Spacer(Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = textColor)
    }
}
