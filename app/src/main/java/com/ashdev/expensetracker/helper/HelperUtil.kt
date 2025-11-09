package com.ashdev.expensetracker.helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.ashdev.expensetracker.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun logit(msg: Any? = "...") {
    if (BuildConfig.DEBUG) {
        val trace: StackTraceElement? = Thread.currentThread().stackTrace[3]
        val lineNumber = trace?.lineNumber
        val methodName = trace?.methodName
        val className = trace?.fileName?.replaceAfter(".", "")?.replace(".", "")

        Log.d("Line $lineNumber", "$className::$methodName() L$lineNumber -> $msg")
    }
}

fun Boolean?.isTrue() = this == true
fun Boolean?.isFalse() = this == false


fun String?.isEqualExt(str: String?): Boolean {
    return this?.equals(str,true) == true
}

infix fun Context.showToast(msg: Any?) {
    val m = msg.toString()
    if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(this, m, if (m.length < 15) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    } else {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, m, if (m.length < 15) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
                .show()
        }
    }
}

fun formatDateFromMilliseconds(milliseconds: Long, format: String = "dd-MM-yyyy"): String {
    val date = Date(milliseconds)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date)
}