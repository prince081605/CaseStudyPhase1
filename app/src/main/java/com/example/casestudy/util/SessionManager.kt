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
        return prefs.getBoolean("is_dark_mode", true)
    }

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("is_dark_mode", enabled).apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}
