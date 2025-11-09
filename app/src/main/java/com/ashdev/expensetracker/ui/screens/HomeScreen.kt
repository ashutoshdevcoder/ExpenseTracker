package com.ashdev.expensetracker.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.model.ExpenseInfo
import com.ashdev.expensetracker.ui.theme.darkBlue
import com.ashdev.expensetracker.ui.theme.regularFont
import com.ashdev.expensetracker.ui.theme.semiBoldFont
import com.ashdev.expensetracker.viewmodel.ExpensesEvent
import com.ashdev.expensetracker.viewmodel.ExpensesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(homeViewModel: ExpensesViewModel?, onClicked: (Screens?) -> Unit) {

    val expenseListState = homeViewModel?.getExpensesInfoState?.collectAsStateWithLifecycle()
    val selectedItems = remember { mutableStateOf(setOf<ExpenseInfo>()) }
    val selectionMode = remember { mutableStateOf(false) }
    val totalAmount = remember {
        derivedStateOf {
            expenseListState?.value?.sumOf { it.amount.toDouble() }
        }
    }
    val monthName = remember {
        SimpleDateFormat("MMMM yyyy", Locale.getDefault())
            .format(Date())
    }
    LaunchedEffect(true) {
        homeViewModel?.onEvent(ExpensesEvent.GetExpensesByMonth(monthName))
    }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().weight(1f)
                    .padding(horizontal = 20.dp),
                text = monthName,
                style = semiBoldFont.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = colorResource(R.color.redFavorite),)
            )

            AnimatedVisibility(selectionMode.value.not(), modifier = Modifier.testTag("AddButton")) {
                IconButton(modifier = Modifier.padding(horizontal = 10.dp),
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White,
                        disabledContentColor = Color.Transparent,
                        containerColor = darkBlue),
                    onClick = {
                    onClicked(Screens.SaveInfoScreen(false))
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                }
            }
            AnimatedVisibility(selectionMode.value) {
                IconButton(modifier = Modifier.padding(end = 10.dp),colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White,
                    disabledContentColor = Color.Transparent,
                    containerColor = darkBlue
                ),onClick = {
                    selectedItems.value.forEach {
                        homeViewModel?.onEvent(ExpensesEvent.DeleteExpenseItem(it._id))
                        selectionMode.value =false
                    }
                }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.White)
                }
            }
            AnimatedVisibility(selectionMode.value) {
                IconButton(modifier = Modifier.padding(end = 10.dp),colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White,
                    disabledContentColor = Color.Transparent,
                    containerColor = darkBlue
                ),onClick = {
                    if(selectedItems.value.size == expenseListState?.value?.size)
                    {
                        selectionMode.value = false
                        selectedItems.value = emptySet()
                    }
                    else
                        selectedItems.value = expenseListState?.value?.toSet() ?: emptySet()

                }) {
                    Icon(if(selectedItems.value.size == expenseListState?.value?.size) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle, contentDescription = "Add", tint = Color.White)
                }


            }

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .background(
                    color = colorResource(
                        R.color.blue_trans
                    ), shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                text = stringResource(R.string.total_expense),
                style = semiBoldFont.copy(
                fontSize = 18.sp,
                color = colorResource(R.color.redFavorite),)
            )
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                text = stringResource(R.string.str_rupee) + ((totalAmount.value) ?: 0.0),
                style = semiBoldFont.copy(
                color = colorResource(R.color.redFavorite),
                fontSize = 20.sp,
                textAlign = TextAlign.End,)
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn()  {
                items(expenseListState?.value.orEmpty(), key = { it -> it to selectedItems.value.contains(it) }) { expenseInfo ->
                    ItemView(onClicked, expenseInfo, selectedItems,selectionMode)
                }


            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemView(
    onClicked: (Screens?) -> Unit,
    it: ExpenseInfo,
    selectedItems: MutableState<Set<ExpenseInfo>>,
    selectionMode: MutableState<Boolean>
) {
    val isSelected = selectedItems.value.contains(it)

    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(if(isSelected) Color.LightGray else Color.White)
                .combinedClickable(
                    onClick = {
                        if(selectionMode.value){
                            selectedItems.value = if (isSelected) {
                                selectedItems.value - it
                            } else {
                                selectedItems.value + it
                            }
                            if (selectedItems.value.isEmpty()) selectionMode.value = false
                        }
                        else
                            onClicked(Screens.SaveInfoScreen(false, it._id))
                    },
                    onLongClick = {
                        selectionMode.value = true
                        selectedItems.value += it
                    }
                )
                .padding(horizontal = 10.dp)) {
            Text(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(color = darkBlue)
                    }
                    .padding(10.dp),
                text = it.itemName.first().toString(),
                style = semiBoldFont.copy(
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


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(null, {})
}