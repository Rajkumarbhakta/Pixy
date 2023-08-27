package com.rkbapps.pixy.imagedetails.models

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val thumb: String,
    val small_s3: String,
) {
    constructor() : this("", "", "", "", "")
}
