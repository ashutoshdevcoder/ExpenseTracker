package com.ashdev.expensetracker.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.model.ExpenseInfo
import com.ashdev.expensetracker.ui.customView.CustomAppBar
import com.ashdev.expensetracker.ui.theme.darkBlue
import com.ashdev.expensetracker.ui.theme.mediumFont
import com.ashdev.expensetracker.ui.theme.regularFont
import com.ashdev.expensetracker.viewmodel.ExpensesEvent
import com.ashdev.expensetracker.viewmodel.ExpensesViewModel

@Composable
fun ExpensesHistoryDetailScreen(
    expenseHistoryViewModel: ExpensesViewModel?,
    monthName: String?,
    onClicked: (Screens?) -> Unit
){
    val expenseListState = expenseHistoryViewModel?.getExpensesInfoState?.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        expenseHistoryViewModel?.onEvent(ExpensesEvent.GetExpensesByMonth(monthName.orEmpty()))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CustomAppBar(
            containerColor = Color.White,
            titleContentColor = darkBlue,
            navigationIconColor = darkBlue,
            onBackButtonClick = {
                onClicked(null)
            }, title = "$monthName Expense Details"
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn()  {
                items(expenseListState?.value.orEmpty()) { expenseInfo ->
                    ItemViewExpense(expenseInfo)
                }


            }
        }


    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemViewExpense(
    it: ExpenseInfo,
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 10.dp)) {
            Text(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(color = darkBlue)
                    }
                    .padding(10.dp),
                text = it.itemName.first().toString(),
                style = mediumFont.copy(
                    fontSize = 20.sp,
                    color = Color.White,)

            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 15.dp),
                    text = it.itemName,
                    style = regularFont.copy(
                        fontSize = 16.sp,)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                    text = it.date,
                    style = regularFont.copy(
                        fontSize = 16.sp,)
                )

            }
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, top = 15.dp),
                text = stringResource(R.string.str_rupee) + " " + it.amount,
                style = regularFont.copy(
                    fontSize = 16.sp,
                    color = colorResource(R.color.redFavorite),)
            )
        }
        HorizontalDivider(thickness = 1.dp, color = colorResource(R.color.colorGreyE0))
    }

}