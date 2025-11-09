package com.ashdev.expensetracker.database

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ashdev.expensetracker.model.ExpenseInfo
import kotlinx.coroutines.flow.Flow

@Dao
@Keep
interface ExpenseInfoDao {
    @Transaction
    fun insertExpenseInfo(items: List<ExpenseInfo>){
        items.forEach {
            insertExpenseInfo(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertExpenseInfo(item: ExpenseInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListExpenseInfo(item: List<ExpenseInfo>)

    @Query("SELECT * FROM expense_info")
    fun getExpenseInfoLiveData() : Flow<List<ExpenseInfo>>

    @Query("SELECT * FROM expense_info where monthName = :monthName ORDER BY " + "substr(date, 7, 4) || '-' || " +  // year
            "substr(date, 4, 2) || '-' || " +  // month
            "substr(date, 1, 2) ASC")
    fun getExpenseInfoByMonth(monthName: String) : Flow<List<ExpenseInfo>>

    @Query("SELECT monthName FROM expense_info")
    fun getMonthListLiveData() : Flow<List<String>>

    @Query("SELECT * FROM expense_info where _id = :id")
    fun getExpenseInfo(id : Int) : Flow<ExpenseInfo>


    @Update
    fun updateExpenseInfo(item: ExpenseInfo)

    @Query("DELETE FROM expense_info where _id = :id")
    fun deleteExpenseInfo(id : Int)
}