package com.lumina.studios

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityAdminDocumentationBinding

class AdminDocumentationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminDocumentationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDocumentationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        val documentation = """
            # Lumina Studios Application Documentation

            ## 1. Project Overview
            Lumina Studios is a premium photography and videography management application. It features a futuristic "Cyber-Studio" aesthetic and serves as a digital portfolio for clients and a management terminal for administrators.

            ## 2. Core Features
            - Multi-Page Hub (Story, Archive, Services, Pricing, Contact)
            - Advanced Booking System (Calendar, Time Slots, Dynamic Costing)
            - Admin Dashboard (Services, Media, and Booking Management)

            ## 3. Technical Specifications
            - Language: Kotlin
            - Layout: XML with ViewBinding
            - Data Management: SessionManager (SharedPreferences)
            
            ## 4. Administrative Security
            - Exclusive Admin: Obed (obedobare212@gmail.com)
            - Secure redirect to Admin Terminal upon authentication.
            - Master Key for admin creation (LUMINA_2024).

            ## 5. UI/UX Design Language
            - Glassmorphism (bg_glass)
            - Fade and Scale ViewPager Transitions
            - Contrast-rich Dark Theme
        """.trimIndent()

        binding.docsContent.text = documentation
    }
}
