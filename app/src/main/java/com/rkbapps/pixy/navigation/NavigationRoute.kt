package com.rkbapps.pixy.navigation

sealed class NavigationRoute(val route: String) {

    object ImageDetails : NavigationRoute(route = "image_details")
    object TopicDetails : NavigationRoute(route = "topic_details")
    object CollectionDetails : NavigationRoute(route = "collection_details")
    object Search : NavigationRoute(route = "search")
}