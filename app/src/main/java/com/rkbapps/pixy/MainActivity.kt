package com.rkbapps.pixy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rkbapps.pixy.navigation.BottomNavigationItem
import com.rkbapps.pixy.navigation.BottomNavigationNavGraph
import com.rkbapps.pixy.screens.BottomNavigation
import com.rkbapps.pixy.screens.TopAppBar
import com.rkbapps.pixy.ui.theme.PixyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PixyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}


@Composable
fun MainApp() {
    val navController = rememberNavController()
    val selectedIndex = rememberSaveable { mutableStateOf(0) }
    val appBarTitle = rememberSaveable {
        mutableStateOf("Pixy")
    }
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Categories,
        BottomNavigationItem.Fav,
        BottomNavigationItem.Profile
    )
    Scaffold(topBar = { TopAppBar(title = appBarTitle, navController = navController, items) },
        bottomBar = { BottomNavigation(selectedIndex, items, navController) }) { contentPadding ->
        BottomNavigationNavGraph(
            navController = navController,
            innerPadding = contentPadding,
            appBarTitle = appBarTitle
        )
    }
}



