package com.ashdev.expensetracker.prefsHelper

interface IEncryptedPreferenceHelper {
    fun setIntroSeen(isIntroSeen: Boolean)
    fun isIntroSeen(): Boolean?

    fun setPasswordRegistered(isRegistered: Boolean)
    fun isPasswordRegistered(): Boolean?

    fun setBiometricLoginEnabled(isEnabled: Boolean)
    fun isBiometricLoginEnabled(): Boolean?

}