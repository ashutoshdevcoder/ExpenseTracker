package com.ashdev.expensetracker.ui.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ashdev.expensetracker.viewmodel.ExpensesViewModel
import com.ashdev.expensetracker.viewmodel.SaveInfoViewModel

@Composable
fun HomeNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HomeScreen) {
        composable<Screens.HomeScreen>(enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(300)
            )
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(300)
            )
        }
        ) {
            val homeViewModel: ExpensesViewModel = hiltViewModel()
            HomeScreen(homeViewModel, onClicked = { screens ->
                if (screens != null)
                    navController.navigate(screens)
                else
                    navController.navigateUp()
            })

        }
        composable<Screens.ExpensesHistoryScreen>(enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(300)
            )
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(300)
            )
        }
        ) {
            val expensesHistoryViewModel:ExpensesViewModel = hiltViewModel()
            ExpensesHistoryScreen(expensesHistoryViewModel){ screens ->
                if (screens != null)
                    navController.navigate(screens)
                else
                    navController.navigateUp()

            }

        }
        composable<Screens.ExpensesHistoryDetailScreen>(enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(300)
            )
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(300)
            )
        }
        ) {
            val monthName = it.toRoute<Screens.ExpensesHistoryDetailScreen>().monthName
            val expensesHistoryViewModel:ExpensesViewModel = hiltViewModel()
            ExpensesHistoryDetailScreen(expensesHistoryViewModel,monthName){ screens ->
                if (screens != null)
                    navController.navigate(screens)
                else
                    navController.navigateUp()

            }

        }

        composable<Screens.SaveInfoScreen>(enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(300)
            )
        }, exitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(300)
            )
        }
        ) {
            val id = it.toRoute<Screens.SaveInfoScreen>().id
            val saveInfoViewModel: SaveInfoViewModel = hiltViewModel()
            SaveInfoScreen(id, saveInfoViewModel, onClicked = { screens ->
                if (screens != null)
                    navController.navigate(screens)
                else
                    navController.navigateUp()
            })
        }
    }
}