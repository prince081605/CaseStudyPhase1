package com.example.casestudy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.casestudy.ui.screens.*

@Composable
fun AppNavHost(
    startDestination: String = "login",
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(navController, isDarkMode)
        }
        composable("campus") {
            CampusInfoScreen(navController)
        }
        composable("tasks") {
            TasksScreen(navController)
        }
        composable("announcements") {
            AnnouncementsScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController, isDarkMode, onThemeChange)
        }
    }
}
