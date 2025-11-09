package com.ashdev.expensetracker.model

sealed class AuthUiState {
    object Initial : AuthUiState()
    object BiometricNotAvailable : AuthUiState()
    object RegistrationRequired : AuthUiState()
    object ReadyForAuth : AuthUiState()
}