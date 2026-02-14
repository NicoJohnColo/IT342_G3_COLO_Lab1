package com.g3.bookbud.utils

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(
            "bookbud_prefs", Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_TOKEN    = "jwt_token"
        private const val KEY_USERNAME = "username"
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, "Bearer $token").apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveUsername(username: String) {
        prefs.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun isLoggedIn(): Boolean = getToken() != null

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}