## 📱 Mobile Development Case Study App

A student academic companion app developed using Kotlin and Jetpack Compose.
The application provides features for task management, campus information, announcements, and account settings, designed with Material Design 3 and a modern dark theme UI.

## 📱 Features
1️⃣ Authentication System

- Login screen with username/password validation
- Hardcoded demo credentials:
    - Username: admin
    - Password: 1234
- Password visibility toggle
- Session persistence using SharedPreferences
- Error handling for invalid credentials

2️⃣ Dashboard
- Personalized welcome message
- Navigation Drawer for accessing all screens
- Recent announcement preview with "NEW" badge
- Quick stats cards displaying:
    - Pending tasks count
    - Today's classes
- Campus information shortcut
- Logout functionality

3️⃣ Campus Information
**Academic Colleges**
- Displayed using Material3 Chips
    - College of Computing Studies
    - College of Engineering
    - College of Education
    - College of Business and Accountancy
    - College of Arts and Sciences
    
### Contact Information
| Type | Details |
|------|---------|
| Email | info@pnc.edu.ph |
| Phone | (049) 545-5453 |
| Address | Barangay Banay-Banay, Cabuyao City, Laguna |
| Office Hours | Monday – Friday, 8:00 AM – 5:00 PM |

4️⃣ Tasks & Schedule Manager

- List of upcoming academic tasks
- Due dates displayed on each card
- Event icons for visual organization
- Floating Action Button (FAB) for adding tasks

**Sample Tasks**
- Capstone Proposal — Feb 5
- Database Quiz — Feb 8
- Mobile Dev Activity — Feb 10

5️⃣ Announcements System

- Card-based announcement layout
- Multiple icon types:
    - Event
    - Info
    - Schedule
- Date stamps for each announcement

**Sample Announcements**
- Enrollment starts next week
- No classes on Feb 6
- Library hours extended

6️⃣ Settings Screen

**Account**
- Username display
- Change password option

**Notifications**
- Toggle push notifications

**Appearance**
-Dark mode toggle

**Language**
- Default language: English

**Privacy & Security**
- Password management
- Two-Factor Authentication (2FA)
- App permissions

**Support & Help**
- FAQ
- Contact support
- Terms & Conditions

**App Info**
- Application version information

## 🛠️ Tech Stack

| Technology           | Version   | Purpose                 |
|---------------------|-----------|------------------------|
| Kotlin              | 1.9+      | Primary programming language |
| Jetpack Compose     | 2024.02.00| Modern UI toolkit       |
| Navigation Compose  | 2.7.7     | In-app navigation       |
| Material Design 3   | 1.2.0     | UI component library    |
| Android SDK         | API 34+   | Platform support        |


## 📁 Project Structure
com.example.casestudy/
│
├── MainActivity.kt              # Application entry point
│
├── ui/
│   ├── navigation/              # Navigation graph
│   │   └── AppNavHost.kt
│   │
│   ├── screens/                 # UI Screens (Composables)
│   │   ├── LoginScreen.kt
│   │   ├── DashboardScreen.kt
│   │   ├── CampusInfoScreen.kt
│   │   ├── TasksScreen.kt
│   │   ├── AnnouncementsScreen.kt
│   │   └── SettingsScreen.kt
│   │
│   └── theme/                   # App Theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── util/
    └── SessionManager.kt        # Session Management


## 🎨 UI / UX Design

### Color Palette

| Color           | Hex Code  | Usage                     |
|-----------------|-----------|---------------------------|
| Cyan            | #00BCD4   | Primary accent, buttons, icons |
| Dark Background | #0F0F0F   | Main background          |
| Dark Card       | #1A1A1A   | Card backgrounds         |
| White           | #FFFFFF   | Primary text             |
| Gray            | #AAAAAA   | Secondary text           |


**Design Features**
- Dark Theme with cyan accents
- Rounded cards (20–24dp)
- Decorative cyan circles
- Consistent Material Design 3 components
- Gradient screen backgrounds


🔧 Key Implementation Details
**Navigation System**
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


**Session Management**
class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLogin(username: String) {
        prefs.edit().putString("username", username).apply()
    }

    fun getUsername(): String? {
        return prefs.getString("username", null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}


## 📸 Screenshots

Add your application screenshots here.
Example:
Login Screen
Dashboard
Campus Information
Tasks Screen
Announcements Screen
Settings Screen

## 🚀 Getting Started

**Prerequisites**
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK API 34

**Installation**
1️⃣ Clone the repository
git clone https://github.com/prince081605/CaseStudyPhase1.git

2️⃣ Open in Android Studio
- Open the project folder in Android Studio.

3️⃣ Sync Gradle
- Allow Android Studio to sync Gradle dependencies.

4️⃣ Run the Application

**Run on:**
- Android Emulator
- Physical Android Device


## 🔑 Default Login Credentials

| Field    | Value  |
|----------|--------|
| Username | admin  |
| Password | 1234   |


## 📝 Future Enhancements

- Backend API authentication
- Room Database for local storage
- Firebase Cloud Messaging (push notifications)
- Offline support
- Biometric authentication
- Dark / Light theme switch
- Multi-language support

## 👨‍💻 Developer

- Mobile Development Case Study
- Pamantasan ng Cabuyao
- February 2026

