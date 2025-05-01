package com.flopez.core.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.example.common.test.MainCoroutineExtension
import com.example.common.test.TestDispatchers
import com.flopez.core.domain.dispatcher.DispatcherProvider
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.result.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class QueryPagedCharactersUseCaseImplTest {

    private lateinit var queryPagedCharactersUseCase: QueryPagedCharactersUseCase
    private lateinit var testDispatcherProvider: DispatcherProvider
    private lateinit var charactersRepository: CharactersRepository

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setup() {
        testDispatcherProvider = TestDispatchers(mainCoroutineExtension.testDispatcher)
        charactersRepository = mockk()
        queryPagedCharactersUseCase = QueryPagedCharactersUseCaseImpl(
            charactersRepository = charactersRepository,
            dispatcherProvider = testDispatcherProvider
        )
    }


    @Test
    fun `execute should return success if loads more pages correctly`() = runTest {
        coEvery { charactersRepository.loadNextPage() } returns PageResult.ITEMS_AVAILABLE

        val result = queryPagedCharactersUseCase.execute()

        assertThat(result).isInstanceOf(Result.Success::class)

        coVerify {
            charactersRepository.loadNextPage()
        }

        confirmVerified(charactersRepository)
    }

    @Test
    fun `execute should return Result Error if load more pages throws any exception`() = runTest {

        coEvery { charactersRepository.loadNextPage() } throws RuntimeException()

        val result = queryPagedCharactersUseCase.execute()

        assertThat(result).isInstanceOf(Result.Error::class)
        assertThat((result as Result.Error<PageResult, MortyException>).error).isInstanceOf(
            CharacterQueryException.QueryCharactersListException::class)

        coVerify {
            charactersRepository.loadNextPage()
        }

        confirmVerified(charactersRepository)
    }


    @Test
    fun `execute should return Result Error if MortyException is thrown`() = runTest {
        val exception = CharacterQueryException.QueryCharactersListException
        coEvery { charactersRepository.loadNextPage() } throws exception

        val result = queryPagedCharactersUseCase.execute()

        assertThat(result).isInstanceOf(Result.Error::class)
        assertThat((result as Result.Error).error).isEqualTo(exception)

        coVerify { charactersRepository.loadNextPage(any()) }
        confirmVerified(charactersRepository)
    }

}