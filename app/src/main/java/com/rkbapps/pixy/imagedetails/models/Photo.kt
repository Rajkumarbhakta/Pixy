package com.rkbapps.pixy.imagedetails.models


data class Photo(
    val id: String,
    val slug: String,
    val created_at: String,
    val color: String,
    val blur_hash: String,
    val alt_description: String?,
    val urls: Urls,
    val links: Links,
    val likes: Int,
    val user: Users,
    val location: Location,
    val tags_preview: List<TagsPreview>,
    val downloads: Int,
) {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        Urls(),
        Links(),
        0,
        Users(),
        Location(),
        emptyList(),
        0
    )
}
