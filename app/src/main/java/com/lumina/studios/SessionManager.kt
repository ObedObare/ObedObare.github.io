package com.lumina.studios

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("lumina_session", Context.MODE_PRIVATE)

    // Specific Admin Credentials
    private val ADMIN_EMAIL = "obedobare212@gmail.com"
    private val ADMIN_PWD = "12345"
    private val ADMIN_NAME = "Obed"

    fun signUp(name: String, email: String, password: String): Result<Unit> {
        if (prefs.contains("user_${email}_pwd")) {
            return Result.failure(Exception("Account already exists"))
        }
        prefs.edit()
            .putString("user_${email}_pwd", password)
            .putString("user_${email}_name", name)
            .apply()
        signIn(email, password)
        return Result.success(Unit)
    }

    fun signIn(email: String, password: String): Result<Unit> {
        val stored = prefs.getString("user_${email}_pwd", null)
            ?: return Result.failure(Exception("No account with that email"))
        if (stored != password) return Result.failure(Exception("Incorrect password"))
        val name = prefs.getString("user_${email}_name", email) ?: email
        prefs.edit()
            .putString("current_email", email)
            .putString("current_name", name)
            .putBoolean("is_admin", false)
            .apply()
        return Result.success(Unit)
    }

    fun signInAdmin(email: String, password: String): Result<Unit> {
        // Only allow the specific hardcoded admin
        if (email == ADMIN_EMAIL && password == ADMIN_PWD) {
            prefs.edit()
                .putString("current_email", email)
                .putString("current_name", ADMIN_NAME)
                .putBoolean("is_admin", true)
                .apply()
            return Result.success(Unit)
        }
        return Result.failure(Exception("Access Denied: Invalid Administrative Credentials"))
    }

    fun signOut() {
        prefs.edit()
            .remove("current_email")
            .remove("current_name")
            .remove("is_admin")
            .apply()
    }

    val currentName: String? get() = prefs.getString("current_name", null)
    val isLoggedIn: Boolean get() = prefs.contains("current_email")
    val isAdmin: Boolean get() = prefs.getBoolean("is_admin", false)
}
