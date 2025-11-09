package com.ashdev.expensetracker.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashdev.expensetracker.ui.screens.HomeContainerScreen
import com.ashdev.expensetracker.ui.screens.IntroScreen
import com.ashdev.expensetracker.ui.screens.LoginScreen
import com.ashdev.expensetracker.ui.screens.Screens
import com.ashdev.expensetracker.ui.screens.SetPinScreen
import com.ashdev.expensetracker.ui.screens.SplashScreen
import com.ashdev.expensetracker.viewmodel.IntroScreenViewModel
import com.ashdev.expensetracker.viewmodel.SetPinViewModel
import com.ashdev.expensetracker.viewmodel.SplashViewModel


@Composable
fun NavGraph() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.SplashScreen,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            }) {


        composable<Screens.SplashScreen>(enterTransition = {
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
                val splashViewModel:SplashViewModel = hiltViewModel()
                SplashScreen(splashViewModel,onClicked = { screens ->
                    if(screens!=null)
                        navController.navigate(screens){
                            popUpTo(0)
                        }
                    else
                        navController.navigateUp()
                })

            }

        composable<Screens.HomeContainerScreen>(enterTransition = {
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
            HomeContainerScreen { screens ->
                if(screens!=null)
                    navController.navigate(screens)
                else
                    navController.navigateUp()
            }

            }



            composable<Screens.IntroScreen> {
                val introScreenViewModel: IntroScreenViewModel = hiltViewModel()
                IntroScreen(introScreenViewModel = introScreenViewModel){ screens ->
                    if (screens != null)
                        navController.navigate(screens)
                    else
                        navController.navigateUp()
                }
            }
            composable<Screens.LoginScreen> {
                val setPinViewModel: SetPinViewModel = hiltViewModel()
                LoginScreen(setPinViewModel = setPinViewModel) { screens ->
                    if (screens != null)
                        navController.navigate(screens) {
                            popUpTo(0)
                        }
                    else
                        navController.navigateUp()
                }

            }
            composable<Screens.SetPinScreen> {
                val setPinViewModel: SetPinViewModel = hiltViewModel()
                SetPinScreen(setPinViewModel = setPinViewModel){ screens ->
                    if(screens!=null)
                        navController.navigate(screens){
                            popUpTo(0)
                        }
                    else
                        navController.navigateUp()
                }
            }

        }
    }
}

