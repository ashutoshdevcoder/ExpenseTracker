package com.ashdev.expensetracker.ui.screens

import kotlinx.serialization.Serializable

sealed class Screens {


    @Serializable
    data object HomeContainerScreen: Screens()

    @Serializable
    data object SplashScreen : Screens()

    @Serializable
    data object LoginScreen : Screens()

    @Serializable
    data object IntroScreen : Screens()

    @Serializable
    data object SetPinScreen : Screens()

    @Serializable
    data object ExpensesHistoryScreen : Screens()

    @Serializable
    data class ExpensesHistoryDetailScreen(val monthName: String) : Screens()

    @Serializable
    data object HomeScreen : Screens()

    @Serializable
    data class SaveInfoScreen(val isAddInfo:Boolean,val id:Int?=null) : Screens()

}