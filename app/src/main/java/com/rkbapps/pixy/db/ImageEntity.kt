package com.rkbapps.pixy.db

import androidx.room.Entity

@Entity(tableName = "images", primaryKeys = ["id", "slug"])
data class ImageEntity(
    val id: String,
    val slug: String,
    val rawUrl: String,
    val fullUrl: String,
    val regularUrl: String,
    val thumbUrl: String,
    val smallUrl: String,
    val blurHash: String,
    val htmlLink: String,
    val likes: Int,
    val downloads: Int,
)
