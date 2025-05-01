package com.flopez.core.presentation.screens.characters.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.core.presentation.generator.fakeCharacters
import com.flopez.core.presentation.robot.CharacterDetailScreenUITestRobot
import com.flopez.core.presentation.ui.theme.WikiMortyTheme
import com.flopez.core.presentation.util.TestActivity
import com.flopez.di.modules.DatabaseModule
import com.flopez.di.modules.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class, NetworkModule::class)
class CharacterDetailScreenTest {

    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    private lateinit var robot: CharacterDetailScreenUITestRobot

    private lateinit var viewModel: CharacterDetailViewModel

    @Inject
    lateinit var charactersRepository: CharactersRepository

    var savedStateHandle = SavedStateHandle(mapOf("id" to 1L))


    @Inject
    lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase


    @Before
    fun setup() {
        hiltTestRule.inject()
        robot = CharacterDetailScreenUITestRobot(composeTestRule)
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterByIdUseCase)
    }


    @OptIn(ExperimentalSharedTransitionApi::class)
    private fun setContent() {
        composeTestRule.setContent {

            val state by viewModel.state.collectAsStateWithLifecycle()

            WikiMortyTheme {
                SharedTransitionLayout {
                    AnimatedVisibility(
                        true
                    ) {
                        CharacterDetailView(
                            animatedVisibilityScope = this,
                            state = state,
                            onBack = {},
                            handleIntent = {}
                        )
                    }
                }
            }
        }
    }

    @Test
    fun shouldShowCharacterNotFound() {
        //Eliminar id de savedStateHandle para que repository no lo encuentre
        savedStateHandle = SavedStateHandle()
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterByIdUseCase)

        setContent()

        robot
            .shareButtonIsNOTDisplayed()
            .addToFavoritesButtonIsNOTDisplayed()
            .loadingSpinnerIsDisplayed()
            .awaitFor {
                !viewModel.state.value.isLoading
            }
            .loadingSpinnerIsNOTDisplayed()
            .characterNotFoundIsDisplayed()
    }

    @Test
    fun shouldCharacterInfo() {
        setContent()

        val character = fakeCharacters().first { it.id == 1L }

        robot
            .shareButtonIsNOTDisplayed()
            .addToFavoritesButtonIsNOTDisplayed()
            .loadingSpinnerIsDisplayed()
            .awaitFor {
                !viewModel.state.value.isLoading
            }
            .loadingSpinnerIsNOTDisplayed()
            .topBarTitleIsDisplayed(character.name)
            .characterNameIsDisplayedTwice(character.name)
            .shareButtonIsDisplayed()
            .addToFavoritesButtonIsDisplayed()
            .characterNotFoundIsNOTDisplayed()
    }


    @Test
    fun shouldShowFavoritesToastWhenClicked() {
        setContent()

        val character = fakeCharacters().first { it.id == 1L }

        robot
            .shareButtonIsNOTDisplayed()
            .addToFavoritesButtonIsNOTDisplayed()
            .loadingSpinnerIsDisplayed()
            .awaitFor {
                !viewModel.state.value.isLoading
            }
            .loadingSpinnerIsNOTDisplayed()
            .topBarTitleIsDisplayed(character.name)
            .characterNameIsDisplayedTwice(character.name)
            .shareButtonIsDisplayed()
            .addToFavoritesButtonIsDisplayed()
            .characterNotFoundIsNOTDisplayed()
            .clickOnAddToFavorites()
            .addToFavoritesToastIsDisplayed()
    }
}