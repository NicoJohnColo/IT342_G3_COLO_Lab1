package com.g3.bookbud.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.g3.bookbud.R
import com.g3.bookbud.data.model.RegisterRequest
import com.g3.bookbud.data.network.RetrofitInstance
import com.g3.bookbud.utils.SessionManager
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sessionManager = SessionManager(this)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName  = findViewById<EditText>(R.id.etLastName)
        val etUsername  = findViewById<EditText>(R.id.etUsername)
        val etEmail     = findViewById<EditText>(R.id.etEmail)
        val etPassword  = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin    = findViewById<Button>(R.id.btnGoLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnRegister.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName  = etLastName.text.toString().trim()
            val username  = etUsername.text.toString().trim()
            val email     = etEmail.text.toString().trim()
            val password  = etPassword.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() ||
                    username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            btnRegister.isEnabled = false

            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.api.register(
                            RegisterRequest(username, email, password, firstName, lastName)
                    )
                    if (response.isSuccessful && response.body() != null) {
                        sessionManager.saveToken(response.body()!!.token)
                        sessionManager.saveUsername(username)
                        Toast.makeText(
                                this@RegisterActivity,
                                "Account created!",
                                Toast.LENGTH_SHORT
                        ).show()
                        startActivity(
                                Intent(this@RegisterActivity, DashboardActivity::class.java)
                        )
                        finishAffinity()
                    } else {
                        val error = response.errorBody()?.string() ?: "Registration failed"
                        Toast.makeText(this@RegisterActivity, error, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Cannot connect. Is backend running?",
                            Toast.LENGTH_LONG
                    ).show()
                } finally {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled = true
                }
            }
        }

        btnLogin.setOnClickListener { finish() }
    }
}