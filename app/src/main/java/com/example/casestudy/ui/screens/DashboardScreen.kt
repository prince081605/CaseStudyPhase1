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
import com.example.casestudy.ui.theme.CyanPrimary
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
    val drawerWidth = screenWidth * 0.7f

    // Dynamic colors
    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    val textColor = MaterialTheme.colorScheme.onBackground
    val primaryColor = MaterialTheme.colorScheme.primary

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(drawerWidth),
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Smart CC",
                    color = primaryColor,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Dashboard", color = textColor) },
                    selected = true,
                    icon = { Icon(Icons.Default.Home, null, tint = primaryColor) },
                    onClick = { scope.launch { drawerState.close() } }
                )

                NavigationDrawerItem(
                    label = { Text("Campus Information", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.School, null, tint = primaryColor) },
                    onClick = { navController.navigate("campus") }
                )

                NavigationDrawerItem(
                    label = { Text("Tasks & Schedule", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Event, null, tint = primaryColor) },
                    onClick = { navController.navigate("tasks") }
                )

                NavigationDrawerItem(
                    label = { Text("Announcements", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Campaign, null, tint = primaryColor) },
                    onClick = { navController.navigate("announcements") }
                )

                NavigationDrawerItem(
                    label = { Text("Settings", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Settings, null, tint = primaryColor) },
                    onClick = { navController.navigate("settings") }
                )

                Spacer(Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Logout", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Logout, null, tint = primaryColor) },
                    onClick = {
                        sessionManager.logout()
                        navController.navigate("login")
                    }
                )
            }
        }
    ) {

        Scaffold(
            containerColor = backgroundColor,
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard", color = textColor) },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, null, tint = textColor)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Welcome, $username",
                        color = primaryColor,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        onClick = { navController.navigate("announcements") }
                    ) {
                        Column(Modifier.padding(16.dp)) {

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Icon(Icons.Default.Campaign, null, tint = primaryColor)

                                Spacer(Modifier.width(8.dp))

                                Text("Recent Announcement", color = primaryColor)

                                if (latestAnnouncement != null && !latestAnnouncement.isRead) {
                                    Spacer(Modifier.width(8.dp))

                                    Surface(
                                        color = Color(0xFFE53935),
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
                                color = textColor,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                text = latestAnnouncement?.let { "Posted ${it.date} • Academic Office" } ?: "",
                                color = textColor.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.labelSmall
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = latestAnnouncement?.content ?: "Check back later for updates.",
                                color = textColor,
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
                            colors = CardDefaults.cardColors(containerColor = cardColor),
                            onClick = { navController.navigate("tasks") }
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Icon(Icons.Default.Event, null, tint = primaryColor)
                                Spacer(Modifier.height(8.dp))
                                Text("Tasks", color = primaryColor)
                                Text("$pendingTasks Pending", color = textColor)
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = cardColor)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Icon(Icons.Default.Schedule, null, tint = primaryColor)
                                Spacer(Modifier.height(8.dp))
                                Text("Schedule", color = primaryColor)
                                Text("2 Classes Today", color = textColor)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        onClick = { navController.navigate("campus") }
                    ) {
                        Row(
                            Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.School, null, tint = primaryColor)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Campus Information", color = primaryColor)
                                Text("Facilities, Offices, Contacts", color = textColor)
                            }
                        }
                    }
                }
            }
        }
    }
}
