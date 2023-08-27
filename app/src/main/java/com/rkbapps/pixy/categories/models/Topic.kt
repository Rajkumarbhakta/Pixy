package com.rkbapps.pixy.categories.models

data class Topic(
    val cover_photo: CoverPhoto?,
    val description: String,
    val ends_at: String,
    val featured: Boolean,
    val id: String,
    val published_at: String,
    val slug: String,
    val starts_at: String,
    val status: String,
    val title: String,
    val total_photos: Int,
    val updated_at: String,
    val visibility: String,
)