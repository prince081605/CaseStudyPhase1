package com.example.casestudy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.casestudy.ui.screens.*
import com.example.casestudy.ui.viewmodel.MainViewModel

@Composable
fun AppNavHost(
    viewModel: MainViewModel,
    startDestination: String = "splash"
) {
    val navController = rememberNavController()
    val isDarkMode by viewModel.isDarkMode.collectAsState()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("splash") {
            SplashScreen(navController, isDarkMode)
        }
        composable("user_selection") {
            UserSelectionScreen(navController, isDarkMode)
        }
        composable(
            route = "login/{userType}",
            arguments = listOf(navArgument("userType") { type = NavType.StringType })
        ) { backStackEntry ->
            val userType = backStackEntry.arguments?.getString("userType") ?: "student"
            LoginScreen(navController, isDarkMode, userType)
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
            SettingsScreen(
                navController = navController,
                isDarkMode = isDarkMode,
                onThemeChange = { viewModel.toggleDarkMode(it) }
            )
        }
    }
}
