package com.ashdev.expensetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashdev.expensetracker.model.ExpenseInfo
import com.ashdev.expensetracker.model.PinInfo

@Database(entities = [ExpenseInfo::class, PinInfo::class],version = AppDatabase.Meta.DATABASE_VERSION,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getExpenseInfoDao(): ExpenseInfoDao
    abstract fun getPinInfoDao(): PinInfoDao
    object Meta {
        const val DATABASE_NAME = "exp_tra_database"
        const val DATABASE_VERSION = 2
    }
}