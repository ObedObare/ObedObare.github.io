package com.lumina.studios

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager
    private var signUpMode = false
    private var adminMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(this)

        if (session.isLoggedIn) {
            goToNextScreen()
            return
        }

        updateMode()

        binding.toggleMode.setOnClickListener {
            signUpMode = !signUpMode
            adminMode = false
            updateMode()
        }

        binding.adminLoginToggle.setOnClickListener {
            adminMode = !adminMode
            signUpMode = false
            updateMode()
        }

        binding.submitBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()
            val name = binding.nameInput.text.toString().trim()

            if (email.isEmpty() || password.length < 4) {
                Toast.makeText(this, "Enter a valid email and 4+ char password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = when {
                adminMode -> session.signInAdmin(email, password)
                signUpMode -> {
                    if (name.isEmpty()) { 
                        Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener 
                    }
                    session.signUp(name, email, password)
                }
                else -> session.signIn(email, password)
            }

            result.onSuccess { goToNextScreen() }
                  .onFailure { Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun updateMode() {
        binding.title.text = when {
            adminMode -> "Admin Terminal"
            signUpMode -> "Create Account"
            else -> "Welcome Back"
        }
        
        binding.nameInput.visibility = if (signUpMode) android.view.View.VISIBLE else android.view.View.GONE
        binding.nameDivider.visibility = if (signUpMode) android.view.View.VISIBLE else android.view.View.GONE
        
        binding.submitBtn.text = when {
            adminMode -> "Authenticate Admin"
            signUpMode -> "Sign Up"
            else -> "Log In"
        }
        
        binding.toggleMode.text = if (signUpMode) "Already have an account? Log In" else "New user? Sign up"
        
        binding.adminLoginToggle.text = if (adminMode) "RETURN TO USER LOGIN" else "ADMIN ACCESS"
        binding.adminLoginToggle.setTextColor(if (adminMode) resources.getColor(R.color.accent_blue, null) else resources.getColor(R.color.gold, null))
    }

    private fun goToNextScreen() {
        val intent = if (session.isAdmin) {
            Intent(this, AdminDashboardActivity::class.java)
        } else {
            Intent(this, GalleryActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
