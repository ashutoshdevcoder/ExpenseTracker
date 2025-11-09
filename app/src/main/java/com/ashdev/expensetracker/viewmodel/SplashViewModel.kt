package com.ashdev.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashdev.expensetracker.helper.isFalse
import com.ashdev.expensetracker.helper.isTrue
import com.ashdev.expensetracker.prefsHelper.IEncryptedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(iPreferenceHelper: IEncryptedPreferenceHelper) :
    ViewModel() {
    private val _navigateToHome = MutableStateFlow<NavPageName?>(null)
    val navigateToHome = _navigateToHome.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            when {
                iPreferenceHelper.isIntroSeen().isFalse() -> _navigateToHome.value = NavPageName.INTRO_SCREEN
                iPreferenceHelper.isPasswordRegistered().isFalse() -> _navigateToHome.value =
                    NavPageName.SET_PIN_SCREEN
                else -> _navigateToHome.value = NavPageName.LOGIN_SCREEN
            }
        }
    }
}

enum class NavPageName{
    INTRO_SCREEN,
    LOGIN_SCREEN,
    SET_PIN_SCREEN
}