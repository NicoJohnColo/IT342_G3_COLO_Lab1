package com.example.colo.mini_app.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("colo_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN   = "jwt_token"
        private const val KEY_USERNAME = "username"
    }

    // Save token after login or register
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, "Bearer $token").apply()
    }

    // Get token to send with protected requests like /api/user/me
    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUsername(username: String) {
        prefs.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    // Check if user is logged in - used to protect Dashboard
    fun isLoggedIn(): Boolean = getToken() != null

    // Call this on logout
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}