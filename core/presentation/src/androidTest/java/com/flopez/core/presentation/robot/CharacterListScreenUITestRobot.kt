package com.flopez.core.presentation.robot

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.flopez.core.presentation.R
import com.flopez.core.presentation.generator.fakeCharacters
import com.flopez.core.presentation.util.test.circularProgressIndicatorTAG

class CharacterListScreenUITestRobot(
    private val composeTestRule: ComposeContentTestRule
) {

    private val context : Context = InstrumentationRegistry.getInstrumentation().context

    fun topBarTitleIsDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.wiki_morty)).isDisplayed()
        return this
    }

    fun topBarTitleIsNotDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.wiki_morty)).isNotDisplayed()
        return this
    }


    fun searchLensIconIsDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.search)).isDisplayed()
        return this
    }

    fun searchLensIconIsNOTDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.search)).isNotDisplayed()
        return this
    }

    fun searchBarIsDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.search_placeholder)).isDisplayed()
        return this
    }

    fun searchBarIsNOTDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.search_placeholder)).isNotDisplayed()
        return this
    }

    fun clickOnSearchIcon() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.search)).performClick()
        return this
    }

    fun charactersListIsDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithText(fakeCharacters()[0].name).isDisplayed()
        return this
    }
    fun loadingSpinnerIsDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithTag(circularProgressIndicatorTAG).isDisplayed()
        return this
    }

    fun loadingSpinnerIsNOTDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithTag(circularProgressIndicatorTAG).assertIsNotDisplayed()
        return this
    }

    fun noItemsMessageIsDisplayed() : CharacterListScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.no_results)).assertIsDisplayed()
        return this
    }

    fun awaitFor(delay: Long = 1_500, condition: () -> Boolean) : CharacterListScreenUITestRobot {
        composeTestRule.waitUntil(delay, condition)
        return this
    }

    fun waitForIdle() : CharacterListScreenUITestRobot {
        composeTestRule.waitForIdle()
        return this
    }

}