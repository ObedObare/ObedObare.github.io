package com.lumina.studios

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.lumina.studios.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = Uri.parse("android.resource://$packageName/${R.raw.sample}")
        val mc = MediaController(this)
        mc.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mc)
        binding.videoView.setVideoURI(uri)
        binding.videoView.setOnPreparedListener { it.isLooping = true; binding.videoView.start() }

        binding.backBtn.setOnClickListener { finish() }
    }
}
