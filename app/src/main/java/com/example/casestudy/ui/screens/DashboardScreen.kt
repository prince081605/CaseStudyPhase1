package com.example.casestudy.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.casestudy.ui.viewmodel.MainViewModel
import com.example.casestudy.util.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername() ?: "User"

    val tasks by viewModel.tasks.collectAsState()
    val announcements by viewModel.announcements.collectAsState()

    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.isCompleted }
    val pendingTasks = totalTasks - completedTasks
    val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f

    val latestAnnouncement = announcements.firstOrNull()
    val unreadAnnouncements = announcements.count { !it.isRead }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.75f

    val blackBg = Color(0xFF0F0F0F)
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val red = Color(0xFFE53935)

    // Dynamic Greeting Logic
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }
    val todayDate = SimpleDateFormat("EEEE, MMMM dd", Locale.getDefault()).format(Date())

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(drawerWidth),
                drawerContainerColor = Color(0xFF121212)
            ) {

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Smart CC",
                    color = cyan,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Dashboard") },
                    selected = true,
                    icon = { Icon(Icons.Default.Home, null) },
                    onClick = { scope.launch { drawerState.close() } }
                )

                NavigationDrawerItem(
                    label = { Text("Campus Information") },
                    selected = false,
                    icon = { Icon(Icons.Default.School, null) },
                    onClick = { navController.navigate("campus") }
                )

                NavigationDrawerItem(
                    label = { Text("Tasks & Schedule") },
                    selected = false,
                    icon = { Icon(Icons.Default.Event, null) },
                    badge = {
                        if (pendingTasks > 0) {
                            Badge(containerColor = cyan, contentColor = Color.Black) {
                                Text("$pendingTasks")
                            }
                        }
                    },
                    onClick = { navController.navigate("tasks") }
                )

                NavigationDrawerItem(
                    label = { Text("Announcements") },
                    selected = false,
                    icon = { Icon(Icons.Default.Campaign, null) },
                    badge = {
                        if (unreadAnnouncements > 0) {
                            Badge(containerColor = red, contentColor = Color.White) {
                                Text("$unreadAnnouncements")
                            }
                        }
                    },
                    onClick = { navController.navigate("announcements") }
                )

                NavigationDrawerItem(
                    label = { Text("Settings") },
                    selected = false,
                    icon = { Icon(Icons.Default.Settings, null) },
                    onClick = { navController.navigate("settings") }
                )

                Spacer(Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    icon = { Icon(Icons.Default.Logout, null) },
                    onClick = {
                        sessionManager.logout()
                        navController.navigate("login")
                    }
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard", color = white) },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, null, tint = white)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("announcements") }) {
                            BadgedBox(
                                badge = {
                                    if (unreadAnnouncements > 0) {
                                        Badge(containerColor = red, contentColor = white) {
                                            Text(unreadAnnouncements.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Announcements",
                                    tint = white
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = blackBg)
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(blackBg)
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text(
                    text = todayDate,
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "$greeting, $username",
                    color = cyan,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard),
                    onClick = {
                        latestAnnouncement?.let {
                            if (!it.isRead) {
                                viewModel.updateAnnouncement(it.copy(isRead = true))
                            }
                            navController.navigate("announcements")
                        }
                    }
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Default.Campaign, null, tint = cyan)

                            Spacer(Modifier.width(8.dp))

                            Text("Recent Announcement", color = cyan)

                            if (latestAnnouncement != null && !latestAnnouncement.isRead) {
                                Spacer(Modifier.width(8.dp))

                                Surface(
                                    color = red,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "NEW",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = latestAnnouncement?.title ?: "No announcements",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = latestAnnouncement?.let { "Posted ${it.date} • Academic Office" } ?: "",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = latestAnnouncement?.content ?: "Check back later for updates.",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = darkCard),
                        onClick = { navController.navigate("tasks") }
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Event, null, tint = cyan)
                            Spacer(Modifier.height(8.dp))
                            Text("Tasks", color = cyan)
                            Text("$pendingTasks Pending", color = Color.White)

                            if (totalTasks > 0) {
                                Spacer(Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier.fillMaxWidth(),
                                    color = cyan,
                                    trackColor = Color.DarkGray
                                )
                                Text(
                                    "${(progress * 100).toInt()}% Done",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = darkCard)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Schedule, null, tint = cyan)
                            Spacer(Modifier.height(8.dp))
                            Text("Schedule", color = cyan)
                            Text("2 Classes Today", color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard),
                    onClick = { navController.navigate("campus") }
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.School, null, tint = cyan)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Campus Information", color = cyan)
                            Text("Facilities, Offices, Contacts", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
