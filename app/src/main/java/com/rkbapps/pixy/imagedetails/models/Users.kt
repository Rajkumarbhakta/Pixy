package com.rkbapps.pixy.imagedetails.models

import com.rkbapps.pixy.home.models.ProfileImage

data class Users(
    val id: String,
    val username: String,
    val name: String,
    val links: UserLinks,
    val profile_image: ProfileImage,
) {
    constructor() : this("", "", "", UserLinks(), ProfileImage())
}
