package com.lumina.studios

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }
        binding.callBtn.setOnClickListener {
            Toast.makeText(this, "Dialing +254 700 000 000...", Toast.LENGTH_SHORT).show()
        }
    }
}
