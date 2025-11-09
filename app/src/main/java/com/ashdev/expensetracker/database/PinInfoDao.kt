package com.ashdev.expensetracker.database

import androidx.annotation.Keep
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ashdev.expensetracker.model.ExpenseInfo
import com.ashdev.expensetracker.model.PinInfo
import kotlinx.coroutines.flow.Flow

@Dao
@Keep
interface PinInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPinInfo(item: PinInfo)

    @Query("SELECT EXISTS(SELECT * FROM PinInfo where pin = :pin)")
    suspend fun isPinInfoExist(pin : String) : Boolean

    @Update
    suspend fun updatePinInfo(item: PinInfo)

}