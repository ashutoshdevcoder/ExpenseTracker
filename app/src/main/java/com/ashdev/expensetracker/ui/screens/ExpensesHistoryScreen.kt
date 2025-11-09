package com.ashdev.expensetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashdev.expensetracker.ui.theme.mediumFont
import com.ashdev.expensetracker.viewmodel.ExpensesEvent
import com.ashdev.expensetracker.viewmodel.ExpensesViewModel

@Composable
fun ExpensesHistoryScreen(expenseHistoryViewModel: ExpensesViewModel?,onClicked: (Screens?) -> Unit){

    val monthList = expenseHistoryViewModel?.getMonthListInfoState?.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        expenseHistoryViewModel?.onEvent(ExpensesEvent.GetMonthList)
    }

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp).background(color = androidx.compose.ui.graphics.Color.White)) {
        LazyColumn(contentPadding = PaddingValues(5.dp))  {
            items (monthList?.value.orEmpty()){
                Card(modifier = Modifier.clickable{
                    onClicked(Screens.ExpensesHistoryDetailScreen(it))
                }) {
                    Row(horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = it,
                            style = mediumFont.copy(fontSize = 20.sp))

                        Icon(Icons.Filled.ArrowForwardIos, contentDescription = "")

                    }
                }
                Spacer(modifier = Modifier.size(10.dp))

            }
        }
    }

}

@Composable
@Preview
fun ExpensesHistoryScreenPreview(){
    ExpensesHistoryScreen(null) { }
}