package com.ashdev.expensetracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ashdev.expensetracker.helper.logit
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsertDataUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init(){
    }

    @Test
    fun insert_multiple_data(){
        composeTestRule.onNodeWithContentDescription("splashLogo").assertIsDisplayed()
        composeTestRule.waitUntil(2000){
            composeTestRule.onAllNodesWithTag("AddButton").fetchSemanticsNodes().size==1
        }
        composeTestRule.onNodeWithTag("AddButton").performClick()
        val lazyColumn = composeTestRule.onNodeWithTag("lazyColumn")
        for(i in 0..10) {
            logit("Item no $i")
            composeTestRule.onNodeWithTag("name$i").performTextInput("Vegetable")
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithTag("price$i").performTextInput("300")
            composeTestRule.waitForIdle()
            lazyColumn.performScrollToNode(hasTestTag("date$i"))
            composeTestRule.onNodeWithTag("date$i").performClick()
            composeTestRule.waitForIdle()
            composeTestRule.waitUntil(2000){
                composeTestRule.onNodeWithTag("datePicker").isDisplayed()
            }
            composeTestRule.onNodeWithText("OK").performClick()
            composeTestRule.waitForIdle()
            if(i<10) {
                composeTestRule.onNodeWithText("Add More").performClick()
                composeTestRule.waitForIdle()
            }
        }
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.waitUntil(5000){
            composeTestRule.onAllNodesWithTag("AddButton").fetchSemanticsNodes().size==1
        }
        Thread.sleep(6000)

        //composeTestRule.onNodeWithContentDescription("Add").


    }
}