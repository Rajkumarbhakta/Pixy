package com.rkbapps.pixy.imagedetails.models

data class Location(
    val name: String,
    val country: String,
) {
    constructor() : this("", "")
}
