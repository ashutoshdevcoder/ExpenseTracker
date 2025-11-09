package com.ashdev.expensetracker.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class PinInfo(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val pin: String = ""){

    companion object {
        const val TABLE_NAME = "pin_info"
        fun empty() = PinInfo()
    }
}
