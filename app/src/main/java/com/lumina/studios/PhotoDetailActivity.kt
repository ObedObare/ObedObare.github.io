package com.lumina.studios

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityPhotoDetailBinding

class PhotoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: ""
        val category = intent.getStringExtra("category") ?: ""
        val resId = intent.getIntExtra("resId", 0)

        binding.title.text = title
        binding.category.text = category
        if (resId != 0) binding.image.setImageResource(resId)
        binding.backBtn.setOnClickListener { finish() }
    }
}
