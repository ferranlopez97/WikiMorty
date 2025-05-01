package com.flopez.core.presentation.screens.characters.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase
import com.flopez.core.presentation.fake.CharacterRepositoryFake
import com.flopez.core.presentation.robot.CharacterListScreenUITestRobot
import com.flopez.core.presentation.ui.theme.WikiMortyTheme
import com.flopez.core.presentation.util.TestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalSharedTransitionApi::class)
class CharacterListScreenTest {

    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    private lateinit var robot: CharacterListScreenUITestRobot

    private lateinit var viewModel: CharacterListViewModel

    @Inject
    lateinit var charactersRepository: CharactersRepository

    @Inject
    lateinit var queryPagedCharactersUseCase: QueryPagedCharactersUseCase


    @Before
    fun setup() {
        hiltTestRule.inject()
        robot = CharacterListScreenUITestRobot(composeTestRule)
        viewModel = CharacterListViewModel(charactersRepository, queryPagedCharactersUseCase)
    }


    private fun setContent() {
        composeTestRule.setContent {

            val state by viewModel.state.collectAsStateWithLifecycle()

            WikiMortyTheme {
                SharedTransitionLayout {
                    AnimatedVisibility(
                        true
                    ) {
                        CharacterListView(
                            animatedVisibilityScope = this,
                            state = state,
                            listState = rememberLazyListState(),
                            onCharacterClicked = {},
                            handleIntent = {},
                        )
                    }
                }
            }
        }
    }

    @Test
    fun shouldLoadListIfItemsAvailable() {

        setContent()

        robot
            .loadingSpinnerIsDisplayed()
            .awaitFor { !viewModel.state.value.isLoading }
            .charactersListIsDisplayed()
            .waitForIdle()
            .loadingSpinnerIsNOTDisplayed()

    }

    @Test
    fun shouldShowSearchBarIfSearchIconIsClicked() {

        setContent()
        robot
            .searchBarIsNOTDisplayed()
            .topBarTitleIsDisplayed()
            .searchLensIconIsDisplayed()
            .clickOnSearchIcon()
            .topBarTitleIsNotDisplayed()
            .searchLensIconIsNOTDisplayed()
            .searchBarIsDisplayed()
    }

    @Test
    fun shouldShowNoResultsMessageIfCharacterListIsEmpty() {

        (charactersRepository as CharacterRepositoryFake).characters.clear()

        setContent()

        robot
            .searchBarIsNOTDisplayed()
            .topBarTitleIsDisplayed()
            .searchLensIconIsDisplayed()
            .loadingSpinnerIsDisplayed()
            .awaitFor { !viewModel.state.value.isLoading }
            .loadingSpinnerIsNOTDisplayed()
            .noItemsMessageIsDisplayed()
    }
}