package com.ashdev.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashdev.expensetracker.database.ExpenseInfoDao
import com.ashdev.expensetracker.model.ExpenseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveInfoViewModel @Inject constructor(
    private val expenseInfoDao: ExpenseInfoDao,
) : ViewModel() {

     private val _getExpenseInfoListState = MutableStateFlow<MutableList<ExpenseInfo>>(mutableListOf())
    val getExpenseInfoListState = _getExpenseInfoListState.asStateFlow()

    init {
       addEmptyData()
    }


    fun addEmptyData(){
        val currentList = _getExpenseInfoListState.value.toMutableList()
        currentList.add(ExpenseInfo.empty())
        _getExpenseInfoListState.value = currentList
    }


    fun onEvent(event: SaveInfoEvent) {
        when (event) {
            is SaveInfoEvent.GetExpenseInfoEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    expenseInfoDao.getExpenseInfo(event.id).collectLatest { expInfo ->

                        _getExpenseInfoListState.update { currentList ->
                            currentList.toMutableList().apply {
                              val info =   ExpenseInfo(_id = expInfo._id, itemName = expInfo.itemName, amount = expInfo.amount, date = expInfo.date)
                               this[0] = info
                            }
                        }

                    }
                }

            }

            is SaveInfoEvent.UpdateExpenseInfoEvent ->{
                viewModelScope.launch(Dispatchers.IO) {
                    expenseInfoDao.updateExpenseInfo(event.expenseInfo)
                }
            }

            is SaveInfoEvent.SaveExpenseInfoEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    expenseInfoDao.insertListExpenseInfo(event.expenseInfo)
                }

            }

        }
    }

    fun updateItemName(updatedExpense: ExpenseInfo, index: Int) {
        val currentList = _getExpenseInfoListState.value.toMutableList()

        if (index != -1) {
            currentList[index] = updatedExpense
        } else {
            currentList.add(updatedExpense)
        }

        _getExpenseInfoListState.value = currentList
    }
}

sealed interface SaveInfoEvent {
    data class SaveExpenseInfoEvent(val expenseInfo: List<ExpenseInfo>) : SaveInfoEvent
    data class GetExpenseInfoEvent(val id: Int) : SaveInfoEvent
    data class UpdateExpenseInfoEvent(val expenseInfo: ExpenseInfo):SaveInfoEvent
}