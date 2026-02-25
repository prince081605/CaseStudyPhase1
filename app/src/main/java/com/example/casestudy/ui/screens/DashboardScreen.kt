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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername() ?: "User"

    val tasks by viewModel.tasks.collectAsState()
    val announcements by viewModel.announcements.collectAsState()
    val pendingTasks = tasks.count { !it.isCompleted }
    val latestAnnouncement = announcements.firstOrNull()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.6f

    val blackBg = Color(0xFF0F0F0F)
    val darkCard = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00BCD4)
    val white = Color.White
    val red = Color(0xFFE53935)

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
                    onClick = { navController.navigate("tasks") }
                )

                NavigationDrawerItem(
                    label = { Text("Announcements") },
                    selected = false,
                    icon = { Icon(Icons.Default.Campaign, null) },
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
                    text = "Welcome, $username",
                    color = cyan,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = darkCard)
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
                            style = MaterialTheme.typography.bodyMedium
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
