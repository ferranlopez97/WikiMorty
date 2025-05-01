package com.flopez.core.presentation.robot

import android.content.Context
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.flopez.core.presentation.R
import com.flopez.core.presentation.util.test.circularProgressIndicatorTAG

class CharacterDetailScreenUITestRobot(
    private val composeTestRule: ComposeContentTestRule
) {

    private val context : Context = InstrumentationRegistry.getInstrumentation().context

    fun loadingSpinnerIsDisplayed() : CharacterDetailScreenUITestRobot{
        composeTestRule.onNodeWithTag(circularProgressIndicatorTAG).isDisplayed()
        return this
    }

    fun loadingSpinnerIsNOTDisplayed() : CharacterDetailScreenUITestRobot{

        return this
    }

    fun topBarTitleIsDisplayed(name: String) : CharacterDetailScreenUITestRobot{
        composeTestRule.onAllNodesWithText(name).onFirst().assertIsDisplayed()
        return this
    }

    fun characterNameIsDisplayedTwice(characterName: String) : CharacterDetailScreenUITestRobot {
        composeTestRule.onAllNodesWithText(characterName).assertCountEquals(2)
        return this
    }

    fun characterNotFoundIsDisplayed() : CharacterDetailScreenUITestRobot{
        composeTestRule.onNodeWithText(context.getString(R.string.error_loading_character)).isDisplayed()
        return this
    }

    fun characterNotFoundIsNOTDisplayed() : CharacterDetailScreenUITestRobot{
        composeTestRule.onNodeWithText(context.getString(R.string.error_loading_character)).isNotDisplayed()
        return this
    }

    fun shareButtonIsDisplayed() : CharacterDetailScreenUITestRobot {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.share)).isDisplayed()
        return this
    }

    fun shareButtonIsNOTDisplayed() : CharacterDetailScreenUITestRobot {
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.share)).isNotDisplayed()
        return this
    }

    fun addToFavoritesButtonIsDisplayed() : CharacterDetailScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.add_to_favorites)).isDisplayed()
        return this
    }

    fun clickOnAddToFavorites() : CharacterDetailScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.add_to_favorites)).performClick()
        return this
    }

    fun addToFavoritesToastIsDisplayed() : CharacterDetailScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.added_to_favs)).isDisplayed()
        return this
    }

    fun addToFavoritesButtonIsNOTDisplayed() : CharacterDetailScreenUITestRobot {
        composeTestRule.onNodeWithText(context.getString(R.string.add_to_favorites)).isNotDisplayed()
        return this
    }

    fun awaitFor(delay: Long = 1_500, condition: () -> Boolean) : CharacterDetailScreenUITestRobot {
        composeTestRule.waitUntil(delay, condition)
        return this
    }

}