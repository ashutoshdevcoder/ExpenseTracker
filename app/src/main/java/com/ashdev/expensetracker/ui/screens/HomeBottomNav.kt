package com.ashdev.expensetracker.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import com.ashdev.expensetracker.R

sealed class HomeBottomNav(val screen: Screens, @DrawableRes val unSelectedIcon: Int, @DrawableRes val selectedIcon: Int, val label: String) {
     object Home : HomeBottomNav(Screens.HomeScreen,R.drawable.ic_bottom_nav_home,R.drawable.ic_home_selected,"Home" )
     object History : HomeBottomNav(Screens.ExpensesHistoryScreen,R.drawable.history_unselected,R.drawable.history_selected,"History")
}