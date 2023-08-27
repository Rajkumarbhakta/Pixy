package com.rkbapps.pixy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = GRAPH.ROOT) {


    }


}

object GRAPH {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
}