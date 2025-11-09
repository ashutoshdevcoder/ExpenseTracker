package com.ashdev.expensetracker.ui.customView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.ui.theme.regularFont
import com.ashdev.expensetracker.ui.theme.semiBoldFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    title: String,
    containerColor: Color = colorResource(R.color.colorPrimary),
    navigationIconColor: Color = Color.White,
    titleContentColor: Color = Color.White,
    endText:String?=null,
    action: @Composable RowScope.() -> Unit = {},
    showNavigationButton:Boolean = true

    ) {
    TopAppBar(
        actions = action,
        modifier = modifier,
        windowInsets = TopAppBarDefaults.windowInsets.only(WindowInsetsSides.Horizontal),
        title = {
            Box(
                modifier = Modifier.height(52.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            ) {
                Row(modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = title,
                        style = semiBoldFont.copy(
                        fontSize = 18.sp,
                        color = titleContentColor)
                    )
                    if(endText!=null)
                    Text(
                        text = endText,
                        style = regularFont.copy(
                        fontSize = 18.sp,
                        color = titleContentColor
                    ))
                }

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            navigationIconContentColor = navigationIconColor,
        ),
        navigationIcon = {
            if(showNavigationButton)
            IconButton(onClick = { onBackButtonClick.invoke() }) {
                Icon(painter = painterResource(R.drawable.ic_back_nav), contentDescription = " ")
            }
        }
    )
}

@Preview
@Composable
fun PreviewCustomAppBar() {
    CustomAppBar(
        Modifier
            .fillMaxWidth(), {}, "Hello"
    )
}