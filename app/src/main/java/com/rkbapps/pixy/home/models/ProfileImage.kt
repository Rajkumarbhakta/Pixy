package com.rkbapps.pixy.home.models

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String,
) {
    constructor() : this("", "", "")
}