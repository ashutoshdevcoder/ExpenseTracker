package com.ashdev.expensetracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.ui.theme.boldFont
import com.ashdev.expensetracker.ui.theme.regularFont
import com.ashdev.expensetracker.ui.theme.semiBoldFont
import com.ashdev.expensetracker.viewmodel.IntroScreenViewModel
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.C

@Composable
fun IntroScreen(introScreenViewModel: IntroScreenViewModel?, onClicked: (Screens?) -> Unit){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 20.dp).verticalScroll(
            rememberScrollState()
        )) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.expenses),
                contentDescription = "splashLogo",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .width(80.dp)
                    .height(80.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(), text = stringResource(R.string.take_control_of_your_money),
                style = boldFont.copy(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.track_smarter_spend_wiser_your_simple_powerful_expense_tracker_is_here),
                style = regularFont.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,)
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.know_where_your_money_goes),
                style = boldFont.copy(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.effortlessly_log_expenses_and_master_your_budget),
                style = regularFont.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.your_money_under_control),
                style = boldFont.copy(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.the_easiest_way_to_track_spending_and_reach_your_goals),
                style = regularFont.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.build_better_financial_habits),
                style = boldFont.copy(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.start_your_journey_to_financial_freedom_by_understanding_your_daily_spending),
                style = regularFont.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.stop_guessing_start_tracking),
                style = boldFont.copy(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(), text = stringResource(R.string.get_a_clear_picture_of_your_cash_flow_and_make_smarter_spending_decisions),
                style = regularFont.copy(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,)
            )

            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp),
                onClick = {
                    onClicked(Screens.SetPinScreen)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.colorPrimary)),
            ) {
                Text(
                    text = stringResource(R.string.get_started),
                    style = semiBoldFont.copy(color = Color.White)
                )

            }

        }
    }
}

@Composable
@Preview
fun IntroScreenPreview(){
    IntroScreen(null){

    }
}