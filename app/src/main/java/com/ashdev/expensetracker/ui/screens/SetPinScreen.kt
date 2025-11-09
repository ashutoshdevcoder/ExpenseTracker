package com.ashdev.expensetracker.ui.screens

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.ui.customView.CustomAppBar
import com.ashdev.expensetracker.ui.customView.OtpTextField
import com.ashdev.expensetracker.ui.theme.boldFont
import com.ashdev.expensetracker.ui.theme.darkBlue
import com.ashdev.expensetracker.ui.theme.semiBoldFont
import com.ashdev.expensetracker.viewmodel.SetPinEvent
import com.ashdev.expensetracker.viewmodel.SetPinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SetPinScreen(setPinViewModel: SetPinViewModel?, onClicked: (Screens?) -> Unit) {
    val pinText = remember { mutableStateOf("") }
    val confirmPinText = remember { mutableStateOf("") }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        CustomAppBar(
            containerColor = Color.White,
            titleContentColor = darkBlue,
            navigationIconColor = darkBlue,
            onBackButtonClick = {
                onBackPressedDispatcher?.onBackPressed()
            }, title = stringResource(R.string.set_pin)
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .imeNestedScroll(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(), text = stringResource(R.string.set_up_your_pin),
                    style = boldFont.copy(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                    )
                )
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.enter_your_four_digit_pin),
                    style = semiBoldFont.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.black_60),
                    )
                )
                OtpTextField(otpCount = 4, modifier = Modifier.padding(vertical = 20.dp)) { value ->
                    pinText.value = value
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.confirm_your_four_digit_pin),
                    style = semiBoldFont.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.black_60),
                    )
                )
                OtpTextField(otpCount = 4, modifier = Modifier.padding(vertical = 20.dp)) { value ->
                    confirmPinText.value = value
                }

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                        if (pinText.value.isNotEmpty() && confirmPinText.value.isNotEmpty() && pinText.value.length == 4 && pinText.value.equals(
                                confirmPinText.value,
                                true
                            )
                        ) {
                            setPinViewModel?.onEvent(SetPinEvent.SetPin(pinText.value))
                            onClicked(Screens.LoginScreen)
                        }

                    },
                    modifier = Modifier
                        .width(160.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.colorPrimary)),
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        style = semiBoldFont.copy(color = Color.White)
                    )

                }
            }
        }
    }

}

@Preview
@Composable
fun SetPinScreenPreview(){
    SetPinScreen(null){

    }
}