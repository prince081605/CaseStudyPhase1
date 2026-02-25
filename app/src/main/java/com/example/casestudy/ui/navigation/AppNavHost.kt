package com.example.casestudy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.casestudy.ui.screens.*
import com.example.casestudy.ui.viewmodel.MainViewModel

@Composable
fun AppNavHost(viewModel: MainViewModel, startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(navController, viewModel)
        }
        composable("campus") {
            CampusInfoScreen(navController)
        }
        composable("tasks") {
            TasksScreen(navController, viewModel)
        }
        composable("announcements") {
            AnnouncementsScreen(navController, viewModel)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
    }
}
