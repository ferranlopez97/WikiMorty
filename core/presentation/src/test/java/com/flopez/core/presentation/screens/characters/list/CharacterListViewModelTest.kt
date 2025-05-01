package com.flopez.core.presentation.screens.characters.list

import android.util.Log
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.example.common.test.MainCoroutineExtension
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.result.Result
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase
import com.flopez.core.presentation.screens.characters.fake.CharactersRepositoryFake
import com.flopez.core.presentation.screens.characters.fake.GetPagedCharactersUseCaseFake
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewEffect
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewIntent
import com.flopez.core.presentation.util.preview.fakeCharacter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class CharacterListViewModelTest {


    private lateinit var viewModel: CharacterListViewModel
    private lateinit var charactersRepository: CharactersRepository
    private lateinit var queryPagedCharactersUseCase: QueryPagedCharactersUseCase

    @BeforeEach
    fun setup() {
        mockkStatic(android.util.Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.v(any(), any()) } returns 0

        charactersRepository = CharactersRepositoryFake()
        queryPagedCharactersUseCase = mockk(relaxed = true)
    }

    @Test
    fun `should call queryPagedCharactersUseCase on init`() = runTest {

        val characters = listOf(
            fakeCharacter.copy(name = "rick"),
            fakeCharacter.copy(name = "character 2")
        )

        (charactersRepository as CharactersRepositoryFake).characters.addAll(characters)

        coEvery { queryPagedCharactersUseCase.execute(any()) } returns Result.Success(PageResult.ITEMS_AVAILABLE)

        viewModel = CharacterListViewModel(charactersRepository, queryPagedCharactersUseCase)

        viewModel.state.test {
            awaitItem()
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
        }

        coVerify { queryPagedCharactersUseCase.execute(QueryFilter.ByName("")) }
        confirmVerified(queryPagedCharactersUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `vm should filter characters after debounce and send effect when query changes`() = runTest {
        val characters = listOf(
            fakeCharacter.copy(name = "rick"),
            fakeCharacter.copy(name = "character 2")
        )

        queryPagedCharactersUseCase = GetPagedCharactersUseCaseFake()
        (charactersRepository as CharactersRepositoryFake).characters.addAll(characters)


        viewModel = CharacterListViewModel(charactersRepository, queryPagedCharactersUseCase)

        turbineScope {
            val state = viewModel.state.testIn(backgroundScope)
            val effect = viewModel.effect.testIn(backgroundScope)

            state.awaitItem()

            viewModel.handleIntent(CharacterListViewIntent.FilterQueryChanged("rick"))

            val effectItem = effect.awaitItem()
            assertThat(effectItem).isEqualTo(CharacterListViewEffect.ScrollToTop)
            effect.cancelAndIgnoreRemainingEvents()

            val stateWithFilter = state.awaitItem()
            assertThat(stateWithFilter.charactersFilter.name).isEqualTo("rick")
            assertThat(stateWithFilter.characters).hasSize(1)
            assertThat(stateWithFilter.characters[0].name).isEqualTo("rick")

            state.cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should toggle search bar state when event is received`() = runTest {

        viewModel = CharacterListViewModel(charactersRepository, queryPagedCharactersUseCase)

        viewModel.state.test {
            val firstEmission = awaitItem()
            assertThat(firstEmission.isSearchBarVisible).isFalse()

            viewModel.handleIntent(CharacterListViewIntent.ToggleSearchBar)

            val secondEmission = awaitItem()
            assertThat(secondEmission.isSearchBarVisible).isTrue()

            viewModel.handleIntent(CharacterListViewIntent.ToggleSearchBar)

            val thirdEmission = awaitItem()
            assertThat(thirdEmission.isSearchBarVisible).isFalse()

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should clear filters if ClearFilters event is received`() = runTest {

        viewModel = CharacterListViewModel(charactersRepository, queryPagedCharactersUseCase)

        viewModel.state.test {
            awaitItem()

            viewModel.handleIntent(CharacterListViewIntent.FilterQueryChanged("rick"))

            val stateWithFilterOne = awaitItem()
            assertThat(stateWithFilterOne.charactersFilter.name).isEqualTo("rick")

            viewModel.handleIntent(CharacterListViewIntent.ClearFilter)

            val stateWithFilter = awaitItem()
            assertThat(stateWithFilter.charactersFilter.name).isEqualTo("")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set isLoading to false and send ShowErrorToast effect when queryCharacters fails`() = runTest {

        queryPagedCharactersUseCase = GetPagedCharactersUseCaseFake()

        viewModel = CharacterListViewModel(charactersRepository, queryPagedCharactersUseCase)

        turbineScope {
            val state = viewModel.state.testIn(backgroundScope)
            val effect = viewModel.effect.testIn(backgroundScope)

            val updatedState = state.awaitItem()
            assertThat(updatedState.isLoading).isTrue()

            //devolver error
            (queryPagedCharactersUseCase as GetPagedCharactersUseCaseFake).exception = CharacterQueryException.QueryCharactersListException

            //accion
            viewModel.handleIntent(CharacterListViewIntent.LoadMoreCharacters)

            assertThat(effect.awaitItem()).isEqualTo(CharacterListViewEffect.ShowErrorToast(CharacterQueryException.QueryCharactersListException))
            effect.cancelAndIgnoreRemainingEvents()

            assertThat(state.awaitItem().isLoading).isFalse()
            state.cancelAndIgnoreRemainingEvents()
        }
    }

}