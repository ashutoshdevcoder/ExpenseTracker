package com.ashdev.expensetracker.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ashdev.expensetracker.R

// Set of Material typography styles to start with
val fontFamily= FontFamily(Font(R.font.mazzard_m_regular, weight = FontWeight.Normal),
    Font(R.font.mazzard_m_medium, weight = FontWeight.Medium),
    Font(R.font.mazzard_m_bold, weight = FontWeight.Bold),
    Font(R.font.mazzard_m_semibold, weight = FontWeight.SemiBold))

val regularFont:TextStyle get() =  TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    fontStyle = FontStyle.Normal,
    color = darkBlue
)
val mediumFont:TextStyle get() =  TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    fontStyle = FontStyle.Normal,
    color = darkBlue
)

val semiBoldFont:TextStyle get() =  TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    fontStyle = FontStyle.Normal,
    color = darkBlue
)

val boldFont:TextStyle get() =  TextStyle(
    fontFamily = fontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    fontStyle = FontStyle.Normal,
    color = darkBlue
)

