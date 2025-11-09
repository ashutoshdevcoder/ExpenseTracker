package com.ashdev.expensetracker.ui.screens

import BiometricAuth
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.helper.isFalse
import com.ashdev.expensetracker.helper.isTrue
import com.ashdev.expensetracker.helper.showToast
import com.ashdev.expensetracker.ui.customView.CustomAppBar
import com.ashdev.expensetracker.ui.customView.OtpTextField
import com.ashdev.expensetracker.ui.theme.boldFont
import com.ashdev.expensetracker.ui.theme.darkBlue
import com.ashdev.expensetracker.ui.theme.semiBoldFont
import com.ashdev.expensetracker.viewmodel.SetPinEvent
import com.ashdev.expensetracker.viewmodel.SetPinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(setPinViewModel: SetPinViewModel?, onClicked: (Screens?) -> Unit) {

    val isPinExist = setPinViewModel?.isPinExist?.collectAsStateWithLifecycle()
    val pinText = remember { mutableStateOf("") }
    val isBiometricEnabled = remember { mutableStateOf(false) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val context = LocalContext.current
    LaunchedEffect(isPinExist?.value) {
        isPinExist?.let {
            if (it.value.isTrue()) {
                onClicked(Screens.HomeContainerScreen)
            }
            else if(it.value.isFalse())
            {
                context.showToast("Please Enter Correct Pin ")
            }
            setPinViewModel.resetPinExistValue()
        }

    }


    if(isBiometricEnabled.value){
        BiometricAuth(onAuthSuccess = {
            onClicked(Screens.HomeContainerScreen)
        }, onAuthError = {

        })
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        CustomAppBar(
            containerColor = Color.White,
            titleContentColor = darkBlue,
            navigationIconColor = darkBlue,
            onBackButtonClick = {
                onBackPressedDispatcher?.onBackPressed()
            }, title = stringResource(R.string.login)
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
                        .fillMaxWidth(), text = stringResource(R.string.app_name),
                    style = boldFont.copy(
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                    )
                )
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
                Spacer(Modifier.size(20.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                        isBiometricEnabled.value = true
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.colorPrimary)),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Use fingerprint",
                            style = semiBoldFont.copy(color = Color.White, fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(
                            painter = painterResource(R.drawable.fingerprint),
                            contentDescription = "",
                            Modifier.size(20.dp),
                            colorFilter = ColorFilter.tint(color = Color.White)
                        )
                    }


                }

                Spacer(Modifier.size(20.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                        if (pinText.value.isNotEmpty() && pinText.value.length == 4) {
                            setPinViewModel?.onEvent(SetPinEvent.CheckPinExist(pinText.value))
                        }
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.colorPrimary)),
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = semiBoldFont.copy(color = Color.White, fontSize = 18.sp)
                    )

                }
            }
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(null) {

    }
}