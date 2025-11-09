package com.ashdev.expensetracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.ui.customView.CustomAppBar
import com.ashdev.expensetracker.ui.theme.darkBlue
import com.ashdev.expensetracker.ui.theme.mediumFont
import com.ashdev.expensetracker.ui.theme.regularFont

@Composable
fun HomeContainerScreen(onClicked: (Screens?) -> Unit){

    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        HomeBottomNav.Home,
        HomeBottomNav.History,
    )

    Scaffold (
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                elevation = 2.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                    ?: Screens.HomeScreen::class.qualifiedName.orEmpty()
                bottomNavigationItems.forEach { item ->
                    val selected = currentDestination == item.screen::class.qualifiedName
                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.screen){popUpTo(0)}
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = if (selected) mediumFont.copy(color = Color.Black) else regularFont.copy(color = Color.Gray),
                                fontSize = 14.sp,
                            )
                        },
                        icon = {
                            Image(
                                modifier = Modifier.size(20.dp),
                                painter = if (selected) painterResource(id = item.selectedIcon) else painterResource(
                                    id = item.unSelectedIcon
                                ), contentDescription = ""
                            )
                        })

                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeNavHost(navController = navController)
        }

    }
}