package com.rkbapps.pixy.navigation


import com.rkbapps.pixy.R

sealed class BottomNavigationItem(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : BottomNavigationItem("home", "Home", R.drawable.homehouse)
    object Categories :
        BottomNavigationItem(route = "categories", title = "Categories", icon = R.drawable.apps)

    object Fav : BottomNavigationItem(route = "fav", title = "Fav", icon = R.drawable.heartlovelike)
    object Profile : BottomNavigationItem(
        route = "profile",
        title = "Profile",
        icon = R.drawable.accountuserpersonround
    )
}
