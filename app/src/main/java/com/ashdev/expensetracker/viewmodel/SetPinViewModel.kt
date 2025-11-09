package com.ashdev.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashdev.expensetracker.database.PinInfoDao
import com.ashdev.expensetracker.model.PinInfo
import com.ashdev.expensetracker.prefsHelper.IEncryptedPreferenceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetPinViewModel @Inject constructor(
    val pinInfoDao: PinInfoDao, val iPreferenceHelper: IEncryptedPreferenceHelper
) : ViewModel() {

    private val _isPinExist = MutableStateFlow<Boolean?>(null)
    val isPinExist = _isPinExist.asStateFlow()


    fun onEvent(event:SetPinEvent){
        when(event){
            is SetPinEvent.SetPin -> {
                viewModelScope.launch {
                    pinInfoDao.insertPinInfo(PinInfo(pin = event.pin))
                    iPreferenceHelper.setPasswordRegistered(true)
                }
            }
            is SetPinEvent.CheckPinExist -> {
                viewModelScope.launch {
                    _isPinExist.value = pinInfoDao.isPinInfoExist(event.pin)
                }
            }
        }
    }

}

sealed interface SetPinEvent{
    data class SetPin(val pin:String): SetPinEvent
    data class CheckPinExist(val pin:String): SetPinEvent
}