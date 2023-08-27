package com.rkbapps.pixy.home.models

data class PhotosItem(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val alt_description: String,
    val description: String?,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int,
)