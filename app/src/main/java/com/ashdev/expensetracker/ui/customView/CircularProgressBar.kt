package com.ashdev.expensetracker.ui.customView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.ui.theme.mediumFont

@Composable
fun CustomProgressBar(isVisible: State<Boolean>,
                      modifier: Modifier,
                      message:String = stringResource(R.string.please_wait),
                      ) {
    if (isVisible.value) {
        Dialog(
            onDismissRequest = {}, // Called when the user clicks outside the dialog
            properties = DialogProperties( // Optional properties
                dismissOnBackPress = false, // Prevent dismissing on back press
                dismissOnClickOutside = false // Prevent dismissing on clicking outside
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = modifier,
                        color = Color.White,
                        trackColor = colorResource(R.color.colorPrimary),
                        strokeWidth = 4.dp,
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = message,
                       style = mediumFont
                    )
                }
            }

        }
    }
}


@Composable
@Preview
fun CustomProgressBarPreview() {
    val isProgressBarVisible = remember {
        mutableStateOf(true)
    }
    CustomProgressBar(isVisible = isProgressBarVisible, Modifier.size(64.dp))
}