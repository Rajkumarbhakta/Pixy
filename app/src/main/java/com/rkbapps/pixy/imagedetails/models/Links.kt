package com.rkbapps.pixy.imagedetails.models

data class Links(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String,
) {
    constructor() : this("", "", "", "")
}
