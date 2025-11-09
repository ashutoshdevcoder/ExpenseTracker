package com.ashdev.expensetracker.ui.customView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.ui.theme.semiBoldFont
import kotlinx.coroutines.delay

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpCount: Int = 6,
    onOtpTextChange: (String) -> Unit,
) {

    var hasFocus by remember { mutableStateOf(false) }
    var otpText by remember { mutableStateOf("") }
    var isCursorVisible by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        while (true) {
            isCursorVisible = !isCursorVisible
            delay(600) // Adjust blink speed
        }
    }
    BasicTextField(
        modifier = modifier.onFocusChanged{ focusState ->
            hasFocus = focusState.isFocused

        },
        value = otpText,
        onValueChange = {
            if(it.isDigitsOnly() && it.length <= otpCount)
            {
                otpText = it
                onOtpTextChange(otpText)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    val showCursor = index == otpText.length && isCursorVisible && hasFocus
                    CharView(
                        showCursor,
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    showCursor: Boolean,
    index: Int,
    text: String
) {
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .size(48.dp)
            .background(
                color = colorResource(R.color.colorGreyF5), RoundedCornerShape(8.dp)
            )
            .padding(2.dp)
    ) {

        if (char.isEmpty().not())
            Text(
                text = char,
                style = semiBoldFont.copy(fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            )
        else
            Image(
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.ic_grey_dot),
                contentDescription = null
            )

        if (showCursor) {
            Box(
                modifier = Modifier
                    .size(2.dp, 24.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .alpha(1f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewOtpView() {
    OtpTextField() { value ->
        // otpValue = value
    }
}