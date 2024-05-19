package com.nickcook.onerepmax.list

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nickcook.onerepmax.core.testing.data.backSquat3
import com.nickcook.onerepmax.core.testing.data.benchPress3
import com.nickcook.onerepmax.core.testing.data.deadlift3
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OneRepMaxListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun oneRepMaxListScreenTest() {
        composeTestRule.setContent {
            OneRepMaxListScreen(
                screenState = OneRepMaxListState.Success(
                    listOf(
                        backSquat3,
                        benchPress3,
                        deadlift3
                    )
                ),
                onItemClick = {},
                onTryAgainClick = {}
            )
        }
        scrollToItemAndAssertExists("Back Squat")
        scrollToItemAndAssertExists("325")
        scrollToItemAndAssertExists("Barbell Bench Press")
        scrollToItemAndAssertExists("321")
        scrollToItemAndAssertExists("Deadlift")
        scrollToItemAndAssertExists("320")
    }

    private fun scrollToItemAndAssertExists(targetText: String) {
        composeTestRule.onNodeWithTag("one_rep_max_list").performScrollToNode(hasText(targetText))
            .assertExists()
    }
}