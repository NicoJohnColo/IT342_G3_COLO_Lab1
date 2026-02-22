package com.g3.bookbud.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.g3.bookbud.R
import com.g3.bookbud.data.network.RetrofitInstance
import com.g3.bookbud.utils.SessionManager
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            redirectToLogin()
            return
        }

        val tvWelcome  = findViewById<TextView>(R.id.tvWelcome)
        val btnProfile = findViewById<Button>(R.id.btnViewProfile)
        val btnLogout  = findViewById<Button>(R.id.btnLogout)

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getProfile(
                        sessionManager.getToken()!!
                )
                if (response.isSuccessful && response.body() != null) {
                    tvWelcome.text = "Welcome, ${response.body()!!.firstName}! 📚"
                } else if (response.code() == 401) {
                    sessionManager.clearSession()
                    redirectToLogin()
                }
            } catch (e: Exception) {
                tvWelcome.text = "Welcome back! 📚"
            }
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                try {
                    RetrofitInstance.api.logout(sessionManager.getToken()!!)
                } catch (e: Exception) {
                    // Continue with logout even if API call fails
                }
                sessionManager.clearSession()
                Toast.makeText(this@DashboardActivity, "Logged out", Toast.LENGTH_SHORT).show()
                redirectToLogin()
            }
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}