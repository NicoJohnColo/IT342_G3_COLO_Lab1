package com.g3.bookbud.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.g3.bookbud.R
import com.g3.bookbud.data.network.RetrofitInstance
import com.g3.bookbud.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            redirectToLogin()
            return
        }

        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvFirstName = findViewById<TextView>(R.id.tvFirstName)
        val tvLastName = findViewById<TextView>(R.id.tvLastName)
        val tvCreatedAt = findViewById<TextView>(R.id.tvCreatedAt)
        val tvLastLogin = findViewById<TextView>(R.id.tvLastLogin)
        val tvFullName = findViewById<TextView>(R.id.tvFullName)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getProfile(
                        sessionManager.getToken()!!
                )
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!
                    tvFullName.text = "${user.firstName} ${user.lastName}"
                    tvUsername.text = "@${user.username}"
                    tvEmail.text = user.email
                    tvFirstName.text = user.firstName
                    tvLastName.text = user.lastName
                    tvCreatedAt.text = user.createdAt ?: "N/A"
                    tvLastLogin.text = user.lastLogin ?: "N/A"
                } else if (response.code() == 401) {
                    sessionManager.clearSession()
                    redirectToLogin()
                } else {
                    Toast.makeText(
                            this@ProfileActivity,
                            "Failed to load profile",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                        this@ProfileActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                ).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                try {
                    RetrofitInstance.api.logout(sessionManager.getToken()!!)
                } catch (e: Exception) {
                    // Continue with logout even if API call fails
                }
                sessionManager.clearSession()
                Toast.makeText(this@ProfileActivity, "Logged out", Toast.LENGTH_SHORT).show()
                redirectToLogin()
            }
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}