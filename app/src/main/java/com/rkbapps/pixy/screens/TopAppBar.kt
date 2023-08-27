package com.rkbapps.pixy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rkbapps.pixy.R
import com.rkbapps.pixy.navigation.BottomNavigationItem
import com.rkbapps.pixy.navigation.NavigationRoute

@Composable
fun TopAppBar(
    title: MutableState<String>,
    navController: NavHostController,
    items: List<BottomNavigationItem>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val destination = items.any { it.route == currentDestination?.route }
    if (destination) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 20.dp, start = 16.dp, end = 20.dp, bottom = 5.dp)
        ) {
            Text(
                text = title.value,
                fontFamily = FontFamily(Font(R.font.softers_regular)),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { navController.navigate(route = NavigationRoute.Search.route) }) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.search),
                    contentDescription = "search"
                )
            }

        }
    }
}

