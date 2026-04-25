package com.example.casestudy.util

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLogin(username: String) {
        prefs.edit().putString("username", username).apply()
    }

    fun getUsername(): String? {
        return prefs.getString("username", null)
    }

    fun savePassword(password: String) {
        prefs.edit().putString("password", password).apply()
    }

    fun getPassword(): String {
        return prefs.getString("password", "1234") ?: "1234"
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean("dark_mode", true)
    }

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("dark_mode", enabled).apply()
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun isNotificationsEnabled(): Boolean {
        return prefs.getBoolean("notifications_enabled", true)
    }

    fun saveLastSeenAnnouncementId(id: String) {
        prefs.edit().putString("last_seen_announcement_id", id).apply()
    }

    fun getLastSeenAnnouncementId(): String? {
        return prefs.getString("last_seen_announcement_id", null)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
