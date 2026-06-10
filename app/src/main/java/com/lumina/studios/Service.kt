package com.lumina.studios

data class Service(
    val id: String,
    val name: String,
    val description: String,
    val iconRes: Int,
    val samples: List<Photo>
)
