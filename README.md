
📱 Features
1. Authentication System
Login Screen with username/password validation
Hardcoded credentials for demo: admin / 1234
Password visibility toggle
Session persistence using SharedPreferences
Error handling for invalid credentials
2. Dashboard
Personalized welcome message with username
Navigation Drawer for accessing all screens
Recent announcement preview with "NEW" badge
Quick stats cards showing:
Pending tasks count
Today's classes
Campus information shortcut
Logout functionality
3. Campus Information
Academic Colleges display with Material3 Chips:
College of Computing Studies
College of Engineering
College of Education
College of Business and Accountancy
College of Arts and Sciences
Contact Information:
Email: info@pnc.edu.ph
Phone: (049) 545-5453
Address: Barangay Banay-Banay, Cabuyao City, Laguna
Administrative Office Hours: Monday-Friday, 8:00 AM - 5:00 PM
4. Tasks & Schedule Manager
List of upcoming academic tasks with due dates
Individual task cards with event icons
Floating Action Button (FAB) for adding new tasks
Sample tasks included:
Capstone Proposal - Feb 5
Database Quiz - Feb 8
Mobile Dev Activity - Feb 10
5. Announcements System
Card-based announcement display
Different icon types (Event, Info, Schedule)
Date stamps for each announcement
Sample announcements:
Enrollment starts next week
No classes on Feb 6
Library hours extended
6. Settings Screen
Account: Username display, password change option
Notifications: Enable/disable push notifications toggle
Appearance: Dark mode toggle
Language: Currently set to English
Privacy & Security: Password, 2FA, App Permissions
Support & Help: FAQ, Contact Support, Terms & Conditions
App Info: Version information
🛠️ Tech Stack
Table
Copy
Technology	Version	Purpose
Kotlin	1.9+	Primary programming language
Jetpack Compose	2024.02.00	Modern UI toolkit
Navigation Compose	2.7.7	In-app navigation
Material Design 3	1.2.0	UI component library
Android SDK	API 34+	Platform support
📁 Project Structure
plain
Copy
com.example.casestudy/
├── MainActivity.kt              # Application entry point
├── ui/
│   ├── navigation/              # Navigation graph
│   │   └── AppNavHost.kt
│   ├── screens/                 # UI screens (Composables)
│   │   ├── LoginScreen.kt
│   │   ├── DashboardScreen.kt
│   │   ├── CampusInfoScreen.kt
│   │   ├── TasksScreen.kt
│   │   ├── AnnouncementsScreen.kt
│   │   └── SettingsScreen.kt
│   └── theme/                   # Theme configuration
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── util/
    └── SessionManager.kt        # Session management
🎨 UI/UX Design
Color Palette
Table
Copy
Color Name	Hex Code	Usage
Cyan	#00BCD4	Primary accent, buttons, icons
Dark Background	#0F0F0F	Main background
Dark Card	#1A1A1A	Card backgrounds
White	#FFFFFF	Primary text
Gray	#AAAAAA	Secondary text
Design Features
Dark Theme with cyan accents
Rounded corners (20-24dp) on cards
Decorative cyan circles for visual enhancement
Consistent Material Design 3 components
Gradient backgrounds on screens
🔧 Key Implementation Details
Navigation System
kotlin
Copy
@Composable
fun AppNavHost(startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("campus") { CampusInfoScreen(navController) }
        composable("tasks") { TasksScreen(navController) }
        composable("announcements") { AnnouncementsScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
Session Management
kotlin
Copy
class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLogin(username: String) {
        prefs.edit().putString("username", username).apply()
    }

    fun getUsername(): String? = prefs.getString("username", null)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
📸 Screenshots
Add your app screenshots here
🚀 Getting Started
Prerequisites
Android Studio Hedgehog (2023.1.1) or later
JDK 17 or later
Android SDK with API level 34
Installation
Clone the repository:
bash
Copy
git clone https://github.com/prince081605/CaseStudyPhase1.git
Open the project in Android Studio
Sync Gradle and build the project
Run on an emulator or physical device
Default Login Credentials
Username: admin
Password: 1234
📝 Future Enhancements
[ ] Backend API integration for real authentication
[ ] Room Database for local data persistence
[ ] Firebase Cloud Messaging for push notifications
[ ] Offline support with caching
[ ] Biometric authentication
[ ] Dark/Light theme toggle
[ ] Multi-language support
👨‍💻 Developer
Mobile Development Case Study
Pamantasan ng Cabuyao
February 2026
