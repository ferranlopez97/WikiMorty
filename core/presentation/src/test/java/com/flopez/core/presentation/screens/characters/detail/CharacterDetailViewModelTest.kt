package com.flopez.core.presentation.screens.characters.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.example.common.test.MainCoroutineExtension
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.result.Result
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewEffect
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewIntent
import com.flopez.core.presentation.screens.characters.fake.GetCharacterByIdUseCaseFake
import com.flopez.core.presentation.util.preview.fakeCharacter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    @BeforeEach
    fun setup() {
        getCharacterByIdUseCase = GetCharacterByIdUseCaseFake()
        savedStateHandle = SavedStateHandle(
            initialState = mapOf("id" to 1L)
        )

        //Instancio los viewmodels en cada test para que turbine no se pierda la primera emision del estado, que ocurre despues de llamar al init del viewmodel
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should call queryCharacter on init and sets state isLoading to false either success or failure`() = runTest {
        getCharacterByIdUseCase = mockk()
        coEvery { getCharacterByIdUseCase.execute(1L) } returns Result.Success(fakeCharacter)

        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterByIdUseCase)

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.isLoading).isTrue()
            advanceUntilIdle()

            val state2 = awaitItem()
            assertThat(state2.isLoading).isFalse()
            cancelAndIgnoreRemainingEvents()
        }

        coVerify { getCharacterByIdUseCase.execute(1L) }
        confirmVerified(getCharacterByIdUseCase)
    }

    @Test
    fun `should send CharacterDetailViewEffect if AddToFavorites event is received`() = runTest {
        viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterByIdUseCase)
        viewModel.effect.test {
            viewModel.handleIntent(CharacterDetailViewIntent.AddToFavorites)

            assertThat(awaitItem()).isInstanceOf(CharacterDetailViewEffect.AddedToFav::class)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set isLoading to false if get character from usecase is success`() = runTest {
        viewModel = CharacterDetailViewModel(
            savedStateHandle = savedStateHandle,
            getCharacterByIdUseCase = getCharacterByIdUseCase
        )

        viewModel.state.test {
            val first = awaitItem()
            assertThat(first.isLoading).isTrue()
            assertThat(first.character).isNull()

            val second = awaitItem()
            assertThat(second.isLoading).isFalse()
            assertThat(second.character).isEqualTo(fakeCharacter)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `should set isLoading to false and exception if get character from usecase fails`() = runTest {

        val exception = CharacterQueryException.QuerySingleCharacterException(1L)
        (getCharacterByIdUseCase as GetCharacterByIdUseCaseFake).exception = exception

        viewModel = CharacterDetailViewModel(
            savedStateHandle = savedStateHandle,
            getCharacterByIdUseCase = getCharacterByIdUseCase
        )

        viewModel.state.test {
            val first = awaitItem()
            assertThat(first.isLoading).isTrue()
            assertThat(first.character).isNull()

            val second = awaitItem()
            assertThat(second.isLoading).isFalse()
            assertThat(second.character).isNull()
            assertThat(second.error).isNotNull().isEqualTo(exception)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set exception to InvalidCharacterId if savedStateHandle does not contain a character id`() = runTest {
        savedStateHandle = SavedStateHandle() //sin el id

        viewModel = CharacterDetailViewModel(
            savedStateHandle = savedStateHandle,
            getCharacterByIdUseCase = getCharacterByIdUseCase
        )

        viewModel.state.test {
            val first = awaitItem()
            assertThat(first.isLoading).isTrue()
            assertThat(first.character).isNull()

            val second = awaitItem()
            assertThat(second.isLoading).isFalse()
            assertThat(second.character).isNull()
            assertThat(second.error).isNotNull().isEqualTo(CharacterQueryException.InvalidCharacterId)

            cancelAndIgnoreRemainingEvents()
        }
    }
}