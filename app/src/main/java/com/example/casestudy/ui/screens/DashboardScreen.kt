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
import com.example.casestudy.util.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, isDarkMode: Boolean) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val username = sessionManager.getUsername() ?: "User"

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.6f

    val blackBg = if (isDarkMode) Color(0xFF0F0F0F) else Color(0xFFF5F5F5)
    val cardBg = if (isDarkMode) Color(0xFF1A1A1A) else Color.White
    val cyan = Color(0xFF00BCD4)
    val textColor = if (isDarkMode) Color.White else Color.Black
    val red = Color(0xFFE53935)
    val drawerBg = if (isDarkMode) Color(0xFF121212) else Color.White

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(drawerWidth),
                drawerContainerColor = drawerBg
            ) {

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Smart CC",
                    color = cyan,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )

                NavigationDrawerItem(
                    label = { Text("Dashboard", color = textColor) },
                    selected = true,
                    icon = { Icon(Icons.Default.Home, null, tint = if (isDarkMode) cyan else Color.Gray) },
                    onClick = { scope.launch { drawerState.close() } },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        selectedContainerColor = cyan.copy(alpha = 0.1f)
                    )
                )

                NavigationDrawerItem(
                    label = { Text("Campus Information", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.School, null, tint = Color.Gray) },
                    onClick = { navController.navigate("campus") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )

                NavigationDrawerItem(
                    label = { Text("Tasks & Schedule", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Event, null, tint = Color.Gray) },
                    onClick = { navController.navigate("tasks") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )

                NavigationDrawerItem(
                    label = { Text("Announcements", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Campaign, null, tint = Color.Gray) },
                    onClick = { navController.navigate("announcements") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )

                NavigationDrawerItem(
                    label = { Text("Settings", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Settings, null, tint = Color.Gray) },
                    onClick = { navController.navigate("settings") },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )

                Spacer(Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Logout", color = textColor) },
                    selected = false,
                    icon = { Icon(Icons.Default.Logout, null, tint = Color.Gray) },
                    onClick = {
                        sessionManager.logout()
                        navController.navigate("login")
                    },
                    colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                )
            }
        }
    ) {

        Scaffold(
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
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(Icons.Default.Campaign, null, tint = cyan)

                            Spacer(Modifier.width(8.dp))

                            Text("Recent Announcement", color = cyan)

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

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "Midterm Exams Schedule Released",
                            color = textColor,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "Posted today • Academic Office",
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelSmall
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = "All students must review their exam dates and assigned rooms before next week.",
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
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Event, null, tint = cyan)
                            Spacer(Modifier.height(8.dp))
                            Text("Tasks", color = cyan)
                            Text("3 Pending", color = textColor)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Schedule, null, tint = cyan)
                            Spacer(Modifier.height(8.dp))
                            Text("Schedule", color = cyan)
                            Text("2 Classes Today", color = textColor)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.School, null, tint = cyan)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Campus Information", color = cyan)
                            Text("Facilities, Offices, Contacts", color = textColor)
                        }
                    }
                }
            }
        }
    }
}
