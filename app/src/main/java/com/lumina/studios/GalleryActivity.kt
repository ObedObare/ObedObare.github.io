package com.lumina.studios

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.lumina.studios.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private lateinit var session: SessionManager

    private val photos = listOf(
        Photo("Portrait No. 01", "Portrait", R.drawable.sample1),
        Photo("Editorial — Golden Hour", "Fashion", R.drawable.sample2),
        Photo("Interior Study", "Architecture", R.drawable.sample3),
        Photo("On Set", "Behind The Scenes", R.drawable.sample4)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(this)

        binding.welcomeText.text = "SESSION_OPERATOR // ${session.currentName?.uppercase() ?: "GUEST_INIT"}"

        if (session.isAdmin) {
            binding.adminPanelBtn.visibility = android.view.View.VISIBLE
            binding.adminPanelBtn.setOnClickListener {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
            }
        }

        binding.mainViewPager.adapter = MainAdapter(photos) { photo ->
            val i = Intent(this, PhotoDetailActivity::class.java)
            i.putExtra("title", photo.title)
            i.putExtra("category", photo.category)
            i.putExtra("resId", photo.resId)
            startActivity(i)
        }

        // Add Fade and Margin Separation
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(50))
        compositePageTransformer.addTransformer { page, position ->
            val absPos = Math.abs(position)
            page.alpha = 1.0f - absPos
            // Add a slight scale effect for more "interesting" scroll
            val scale = 0.85f + (1.0f - absPos) * 0.15f
            page.scaleX = scale
            page.scaleY = scale
        }
        binding.mainViewPager.setPageTransformer(compositePageTransformer)

        binding.mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateTabs(position)
            }
        })

        binding.tabStory.setOnClickListener { binding.mainViewPager.currentItem = 0 }
        binding.tabArchive.setOnClickListener { binding.mainViewPager.currentItem = 1 }
        binding.tabModules.setOnClickListener { binding.mainViewPager.currentItem = 2 }
        binding.tabPricing.setOnClickListener { binding.mainViewPager.currentItem = 3 }
        binding.tabBooking.setOnClickListener { binding.mainViewPager.currentItem = 4 }
        binding.tabLink.setOnClickListener { binding.mainViewPager.currentItem = 5 }

        binding.signOutBtn.setOnClickListener {
            session.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun updateTabs(position: Int) {
        val active = resources.getColor(R.color.accent_blue, null)
        val inactive = resources.getColor(R.color.muted, null)
        
        binding.tabStory.setTextColor(if (position == 0) active else inactive)
        binding.tabArchive.setTextColor(if (position == 1) active else inactive)
        binding.tabModules.setTextColor(if (position == 2) active else inactive)
        binding.tabPricing.setTextColor(if (position == 3) active else inactive)
        binding.tabBooking.setTextColor(if (position == 4) active else inactive)
        binding.tabLink.setTextColor(if (position == 5) active else inactive)
        
        binding.pageTitle.text = when(position) {
            0 -> "STORY"
            1 -> "ARCHIVE"
            2 -> "MODULES"
            3 -> "PRICING"
            4 -> "BOOKING"
            5 -> "LINK"
            else -> ""
        }
    }
}
