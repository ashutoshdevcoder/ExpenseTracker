package com.ashdev.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashdev.expensetracker.database.ExpenseInfoDao
import com.ashdev.expensetracker.model.ExpenseInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(private val expenseInfoDao: ExpenseInfoDao) :
    ViewModel() {
    private val _getExpensesInfoState = MutableStateFlow<List<ExpenseInfo>?>(null)
    val getExpensesInfoState : StateFlow<List<ExpenseInfo>?> = _getExpensesInfoState
    private val _getMonthListInfoState = MutableStateFlow<List<String>?>(null)
    val getMonthListInfoState = _getMonthListInfoState.asStateFlow()


    fun onEvent(event: ExpensesEvent){
        when(event){
            is ExpensesEvent.GetExpenseList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    expenseInfoDao.getExpenseInfoLiveData().collectLatest {
                        _getExpensesInfoState.value = it
                    }
                }
            }

            is ExpensesEvent.DeleteExpenseItem -> {
                viewModelScope.launch (Dispatchers.IO){
                    expenseInfoDao.deleteExpenseInfo(event.id)
                    onEvent(ExpensesEvent.GetExpenseList)
                }

            }
            is ExpensesEvent.GetMonthList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    expenseInfoDao.getMonthListLiveData().collectLatest {
                        _getMonthListInfoState.value = it
                    }
                }
            }
            is ExpensesEvent.GetExpensesByMonth -> {
                viewModelScope.launch(Dispatchers.IO) {
                    expenseInfoDao.getExpenseInfoByMonth(event.month).collectLatest {
                        _getExpensesInfoState.value = it
                    }
                }
            }
        }
    }

}

sealed interface ExpensesEvent{
    data object GetExpenseList:ExpensesEvent
    data class DeleteExpenseItem(val id:Int):ExpensesEvent
    data object GetMonthList:ExpensesEvent
    data class GetExpensesByMonth(val month: String):ExpensesEvent
}