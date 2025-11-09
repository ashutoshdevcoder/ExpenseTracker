package com.ashdev.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import com.ashdev.expensetracker.prefsHelper.IEncryptedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroScreenViewModel @Inject constructor(iPreferenceHelper: IEncryptedPreferenceHelper) : ViewModel(){

    init {
        iPreferenceHelper.setIntroSeen(true)
    }
}