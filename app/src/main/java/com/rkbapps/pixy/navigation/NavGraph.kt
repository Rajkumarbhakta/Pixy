package com.rkbapps.pixy.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rkbapps.pixy.categories.screen.CategoriesScreen
import com.rkbapps.pixy.collectiondetails.screen.CollectionDetailsScreen
import com.rkbapps.pixy.home.screen.HomeScreen
import com.rkbapps.pixy.imagedetails.screen.ImageDetailsScreen
import com.rkbapps.pixy.profile.screens.ProfileScreen
import com.rkbapps.pixy.screens.FavScreen
import com.rkbapps.pixy.search.screen.SearchScreen
import com.rkbapps.pixy.topicdetails.screen.TopicDetailsScreen

//@Composable
//fun NavGraph(navController: NavController) {
//
//    //NavHost(navController = navController, startDestination = )
//
//}

@Composable
fun BottomNavigationNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    appBarTitle: MutableState<String>,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.route,
        modifier = Modifier.padding(paddingValues = innerPadding)
    ) {
        composable(route = BottomNavigationItem.Home.route) {
            appBarTitle.value = "Pixy"
            HomeScreen(navController)
        }
        composable(route = BottomNavigationItem.Fav.route) {
            appBarTitle.value = "Fav"
            FavScreen()
        }
        composable(route = BottomNavigationItem.Categories.route) {
            appBarTitle.value = "Categories"
            CategoriesScreen(navController)
        }
        composable(route = BottomNavigationItem.Profile.route) {
            appBarTitle.value = "Profile"
            ProfileScreen()
        }
        composable(route = NavigationRoute.ImageDetails.route + "/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            ImageDetailsScreen()
        }

        composable(route = NavigationRoute.TopicDetails.route + "/{topic_id}/{topic_title}",
            arguments = listOf(
                navArgument("topic_id") {
                    type = NavType.StringType
                },
                navArgument("topic_title") {
                    type = NavType.StringType
                }
            )) {
            TopicDetailsScreen(navController)
        }

        composable(route = NavigationRoute.CollectionDetails.route + "/{collection_id}/{collection_title}",
            arguments = listOf(
                navArgument("collection_id") {
                    type = NavType.StringType
                },
                navArgument("collection_title") {
                    type = NavType.StringType
                }
            )) {
            CollectionDetailsScreen(navController)
        }

        composable(route = NavigationRoute.Search.route) {
            SearchScreen(navController)
        }
    }

}