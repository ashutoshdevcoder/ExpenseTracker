package com.ashdev.expensetracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.helper.isTrue
import com.ashdev.expensetracker.ui.theme.boldFont
import com.ashdev.expensetracker.viewmodel.NavPageName
import com.ashdev.expensetracker.viewmodel.SplashViewModel

@Composable
fun SplashScreen(splashViewModel: SplashViewModel?, onClicked: (Screens?) -> Unit) {
    val navigateToHome = splashViewModel?.navigateToHome?.collectAsStateWithLifecycle()

    LaunchedEffect(navigateToHome?.value) {
        navigateToHome?.value?.let {
            if(navigateToHome.value == NavPageName.SET_PIN_SCREEN)
                onClicked(Screens.SetPinScreen)
            else if(navigateToHome.value == NavPageName.LOGIN_SCREEN)
                onClicked(Screens.LoginScreen)
            else
                onClicked(Screens.IntroScreen)
        }

    }


    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.expenses),
                contentDescription = "splashLogo",
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(), text = stringResource(R.string.app_name),
                style = boldFont.copy(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,)
            )
        }
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    SplashScreen(null, {})
}