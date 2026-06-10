package com.lumina.studios

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.backBtn.setOnClickListener { finish() }
        
        binding.exploreArchiveBtn.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
            // If this was an onboarding style after login, we might finish()
            // but the user might also want to come back to it.
            // Let's finish() if we want it to be a one-way flow from login.
        }
    }
}
