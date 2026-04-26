# 📱 Mobile Development Case Study App

A **modern student academic companion app** built using **Kotlin + Jetpack Compose**, designed for task management, campus information, and announcements.
Now upgraded with **Room Database**, enabling persistent storage and real-time data updates.

---

## ✨ Key Highlights

* 📌 Modern **Jetpack Compose UI**
* 🗄️ **Room Database (SQLite ORM)**
* ⚡ Real-time updates using **Kotlin Flow**
* 🔐 Session-based authentication
* 🌙 Dark-themed Material 3 design
* 📱 Fully offline-capable core features

---

## 📱 Features

### 🔐 Authentication System

* Login with validation (admin / 1234)
* Password visibility toggle
* Session persistence using `SharedPreferences`
* Secure logout handling

---

### 📊 Dashboard

* Personalized welcome screen
* Navigation Drawer system
* **Live Quick Stats (Room-powered):**

  * Pending tasks count
  * Activity overview
* Reactive UI using **Flow + Compose**

---

### 🏫 Campus Information

* College listings via Material Chips:

  * Computing Studies
  * Engineering
  * Education
  * Business & Accountancy
  * Arts & Sciences
* Contact directory (email, phone, address, office hours)

---

### 📌 Task Manager (Room Database)

* Persistent task storage
* Full CRUD operations:

  * Create
  * Read
  * Update
  * Delete
* Due date tracking system
* Floating Action Button (FAB) for task entry

---

### 📢 Announcements System

* Card-based UI design
* Categories:

  * Event
  * Info
  * Schedule
* Offline caching support
* Timestamped updates

---

### ⚙️ Settings Module

* Account management
* Dark mode support 🌙
* Notification toggle 🔔
* Language settings 🌐
* Privacy & security options
* App version info

---

## 🛠️ Tech Stack

| Layer        | Technology              |
| ------------ | ----------------------- |
| Language     | Kotlin 2.0.21           |
| UI           | Jetpack Compose 2024.02 |
| Database     | Room 2.6.1              |
| Architecture | MVVM (Lightweight)      |
| Async        | Kotlin Flow             |
| DI Tool      | KSP                     |
| Navigation   | Navigation Compose      |
| Design       | Material Design 3       |
| Platform     | Android API 35          |
| Build Tool   | Gradle (Kotlin DSL)     |

---

## 📁 Project Structure

```text
com.example.casestudy/
│
├── data/
│   ├── AppDatabase.kt
│   ├── AppDao.kt
│   └── Entities.kt
│
├── ui/
│   ├── navigation/
│   │   └── AppNavHost.kt
│   ├── screens/
│   └── theme/
│
└── util/
    └── SessionManager.kt
```

---

## 🔧 Core Implementation

### 🧭 Navigation

```kotlin
@Composable
fun AppNavHost(startDestination: String = "login") {
    val navController = rememberNavController()

    NavHost(navController, startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("campus") { CampusInfoScreen(navController) }
        composable("tasks") { TasksScreen(navController) }
        composable("announcements") { AnnouncementsScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
    }
}
```

---

### 🗄️ Room Database (DAO)

```kotlin
@Dao
interface AppDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}
```

---

### 🔐 Session Manager

```kotlin
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
```

---

## 🎨 UI / UX Design

### 🎨 Theme

* Cyan Accent: `#00BCD4`
* Dark Background: `#0F0F0F`
* Card Background: `#1A1A1A`
* Text: White / Gray hierarchy

### ✨ Design System

* Material 3 components
* Rounded cards (20–24dp)
* Gradient background effects
* Clean spacing system
* Minimal modern layout

---

## 🚀 Setup Guide

```bash
git clone https://github.com/prince081605/CaseStudyPhase1.git
```

### Steps:

1. Open in Android Studio (Ladybug+)
2. Sync Gradle
3. Run on Emulator / Device

---

## 🔑 Login Credentials

| Username | Password |
| -------- | -------- |
| admin    | 1234     |

---

## 🔮 Future Improvements

* Firebase Authentication 🔐
* Cloud Sync (Realtime DB)
* Push Notifications (FCM)
* Biometric Login
* Multi-language Support
* Full MVVM Architecture upgrade

---

## 👨‍💻 Developer

**Mobile Development Case Study**
Pamantasan ng Cabuyao
April 2026
