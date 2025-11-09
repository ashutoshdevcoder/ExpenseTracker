package com.ashdev.expensetracker.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = ExpenseInfo.TABLE_NAME,
    indices = [Index(value = arrayOf("_id"), unique = true)]
)
data class ExpenseInfo(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    var monthName:String="",
    var itemName:String="",
    var date:String="",
    var amount:String="")
{
    companion object {
        const val TABLE_NAME = "expense_info"
        fun empty() = ExpenseInfo()
    }
}

