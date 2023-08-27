package com.rkbapps.pixy.categories.models

data class CoverPhoto(
    val alt_description: String,
    val blur_hash: String,
    val color: String?,
    val created_at: String,
    val description: String,
    val height: Int,
    val id: String,
    val slug: String,
    val urls: Urls,
    val width: Int,
)