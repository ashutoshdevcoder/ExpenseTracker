package com.ashdev.expensetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ashdev.expensetracker.R
import com.ashdev.expensetracker.helper.formatDateFromMilliseconds
import com.ashdev.expensetracker.helper.logit
import com.ashdev.expensetracker.helper.showToast
import com.ashdev.expensetracker.model.ExpenseInfo
import com.ashdev.expensetracker.ui.customView.CustomAppBar
import com.ashdev.expensetracker.ui.theme.darkBlue
import com.ashdev.expensetracker.ui.theme.regularFont
import com.ashdev.expensetracker.ui.theme.semiBoldFont
import com.ashdev.expensetracker.viewmodel.SaveInfoEvent
import com.ashdev.expensetracker.viewmodel.SaveInfoViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.time.ZoneId
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SaveInfoScreen(
    id: Int?,
    saveInfoViewModel: SaveInfoViewModel?,
    onClicked: (Screens?) -> Unit
) {

    val getExpenseInfoListState = saveInfoViewModel?.getExpenseInfoListState?.collectAsStateWithLifecycle()

    var isEditing by remember { mutableStateOf(true) }
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState(selectableDates = object : SelectableDates{
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return (utcTimeMillis <= System.currentTimeMillis())
        }
    })


    val currentItem = remember { mutableStateOf(ExpenseInfo.empty()) }
    val currentIndex = remember { mutableIntStateOf(0) }
    val lazyColumnState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    if (id != null) {
        LaunchedEffect(Unit) {
            saveInfoViewModel?.onEvent(SaveInfoEvent.GetExpenseInfoEvent(id))
        }
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
            }, title = "Add Information"
        )
        if (showDatePicker) {

            DatePickerDialog(
                modifier = Modifier.testTag("datePicker"),
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                            val date = formatDateFromMilliseconds(dateState.selectedDateMillis ?: System.currentTimeMillis())
                            val d = Date()
                            d.time = dateState.selectedDateMillis ?: System.currentTimeMillis()
                            val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                                .format(d)

                            saveInfoViewModel?.updateItemName(currentItem.value.copy(date = date, monthName = monthName),currentIndex.intValue)
                        }
                    ) { Text("OK") }
                }
            ) {
                DatePicker(
                    state = dateState
                )
            }
        }
        LazyColumn(modifier = Modifier.imePadding().imeNestedScroll().testTag("lazyColumn"), state = lazyColumnState) {
            itemsIndexed(getExpenseInfoListState?.value.orEmpty()){index, item ->
                Box {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        OutlinedTextField(
                            enabled = isEditing,
                            value = item.itemName,
                            onValueChange = {
                                saveInfoViewModel?.updateItemName(item.copy(itemName = it),index)
                                            },
                            label = {
                                Text(
                                    stringResource(R.string.item_name),
                                    style = regularFont
                                )
                            },
                            placeholder = {
                                Text(
                                    stringResource(R.string.vegetable),
                                    style = regularFont.copy(
                                        color = colorResource(R.color.colorGreyE0))
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.testTag("name$index")
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Sentences

                            ),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = darkBlue,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                disabledTextColor = darkBlue


                            )

                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        OutlinedTextField(
                            enabled = isEditing,
                            value = item.amount,
                            onValueChange = {
                                if (it.isEmpty() || it.toDoubleOrNull() != null)
                                    saveInfoViewModel?.updateItemName(item.copy(amount = it),index)
                            },
                            label = {
                                Text(
                                    stringResource(R.string.amount),
                                    style = regularFont                        )
                            },
                            placeholder = {
                                Text(
                                    "100",
                                    style = regularFont.copy(color = colorResource(R.color.colorGreyE0))
                                )
                            },
                            singleLine = true,
                            modifier = Modifier.testTag("price$index")
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = darkBlue,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                disabledTextColor = darkBlue

                            )

                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Box(modifier = Modifier.testTag("date$index")
                            .fillMaxWidth()
                            .clickable {
                                if(isEditing)
                                {
                                    currentItem.value = item
                                    currentIndex.intValue = index
                                    showDatePicker = true
                                    keyboardController?.hide()
                                }
                            }) {
                            OutlinedTextField(
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Select Date"
                                    )
                                },
                                enabled = false,
                                readOnly = true,
                                value = item.date,
                                onValueChange = { },
                                label = {
                                    Text(
                                        stringResource(R.string.date),
                                        style = regularFont                            )
                                },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = darkBlue,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    disabledContainerColor = Color.White,
                                    disabledTextColor = darkBlue,
                                    disabledIndicatorColor = darkBlue

                                )

                            )
                        }

                        Spacer(modifier = Modifier.size(10.dp))

                    }
                }

            }

            if(id==null) {
                item {
                    OutlinedButton(
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(0.dp),
                        onClick = {
                            saveInfoViewModel?.addEmptyData()
                            coroutineScope.launch {
                                lazyColumnState.scrollToItem(
                                    getExpenseInfoListState?.value?.size ?: 0
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.colorPrimary)),
                    ) {
                        Text(
                            text = stringResource(R.string.add_more),
                            style = semiBoldFont.copy(color = Color.White)
                        )

                    }

                }
            }

            item {
                OutlinedButton (
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    onClick = {
                        logit("item ${getExpenseInfoListState?.value}")
                        for (item in getExpenseInfoListState?.value.orEmpty()) {
                            if (item.itemName.isEmpty()) {
                                context.showToast("Please Enter Item Name")
                                break
                            } else if (item.date.isEmpty()) {
                                context.showToast("Please Enter Date")
                                break
                            } else if (item.amount.isEmpty()) {
                                context.showToast("Please Enter Amount")
                                break
                            } else {

                                if(id!=null) {
                                    val expenseInfo = ExpenseInfo(
                                        _id = id,
                                        monthName = item.monthName,
                                        itemName = item.itemName,
                                        date = item.date,
                                        amount = item.amount
                                    )
                                    saveInfoViewModel?.onEvent(
                                        SaveInfoEvent.UpdateExpenseInfoEvent(
                                            expenseInfo
                                        )
                                    )
                                }
                            }
                        }
                        getExpenseInfoListState?.value?.let {
                            saveInfoViewModel.onEvent(
                                SaveInfoEvent.SaveExpenseInfoEvent(
                                    it.toList()
                                )
                            )
                        }
                        onClicked(null)


                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.colorPrimary)),
                ){
                    Text(
                        text = stringResource(R.string.save),
                        style = semiBoldFont.copy(color = Color.White)
                    )

                }

            }

        }






    }
}


@Composable
@Preview
fun SaveInfoScreenPreview() {
    SaveInfoScreen(null, null, { })
}