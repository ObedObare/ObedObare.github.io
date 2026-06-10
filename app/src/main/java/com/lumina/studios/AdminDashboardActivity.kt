package com.lumina.studios

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDashboardBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(this)

        if (!session.isAdmin) {
            Toast.makeText(this, "Unauthorized Access Detected", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        binding.logoutBtn.setOnClickListener {
            session.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.manageServicesBtn.setOnClickListener {
            Toast.makeText(this, "Opening Service Editor Interface...", Toast.LENGTH_SHORT).show()
            // In a real app, this would navigate to a list where you can CRUD services
        }

        binding.managePhotosBtn.setOnClickListener {
            Toast.makeText(this, "Accessing Gallery Media Controls...", Toast.LENGTH_SHORT).show()
            // In a real app, this would open a file picker and update the Photo list
        }

        binding.viewBookingsBtn.setOnClickListener {
            Toast.makeText(this, "Syncing with Booking Database...", Toast.LENGTH_SHORT).show()
            // This would show a list of all requests made via the Booking page
        }

        binding.viewDocsBtn.setOnClickListener {
            startActivity(Intent(this, AdminDocumentationActivity::class.java))
        }
    }
}
