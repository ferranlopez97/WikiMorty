package com.flopez.core.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.example.common.test.MainCoroutineExtension
import com.example.common.test.TestDispatchers
import com.flopez.core.domain.dispatcher.DispatcherProvider
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.generators.character
import com.flopez.core.domain.model.Character
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.result.Result
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class GetCharacterByIdUseCaseImplTest {

    private lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var charactersRepository: CharactersRepository

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    @BeforeEach
    fun setup() {
        dispatcherProvider = TestDispatchers(mainCoroutineExtension.testDispatcher)
        charactersRepository = mockk()
        getCharacterByIdUseCase = GetCharacterByIdUseCaseImpl(
            dispatcherProvider = dispatcherProvider,
            charactersRepository = charactersRepository
        )
    }


    @Test
    fun `execute should return Result Succes with character if exists`() = runTest {

        val idSlot = slot<Long>()
        every { charactersRepository.getCharacterById(capture(idSlot)) } returns character()

        val result = getCharacterByIdUseCase.execute(1)

        assertThat(result).isInstanceOf(Result.Success::class)
        assertThat((result as Result.Success<Character, MortyException>).data.id).isEqualTo(idSlot.captured)

        coVerify {
            charactersRepository.getCharacterById(idSlot.captured)
        }

        confirmVerified(charactersRepository)
    }


    @Test
    fun `execute should return Result Error if characters does not exist (is null)`() = runTest {

        val idSlot = slot<Long>()
        every { charactersRepository.getCharacterById(capture(idSlot)) } returns null

        val result = getCharacterByIdUseCase.execute(1)

        assertThat(result).isInstanceOf(Result.Error::class)
        assertThat((result as Result.Error<Character, MortyException>).error).isInstanceOf(CharacterQueryException.QuerySingleCharacterException::class)

        coVerify {
            charactersRepository.getCharacterById(idSlot.captured)
        }

        confirmVerified(charactersRepository)
    }



    @Test
    fun `execute should return Result Error if any exception is thrown`() = runTest {

        val idSlot = slot<Long>()
        every { charactersRepository.getCharacterById(capture(idSlot)) } throws RuntimeException()

        val result = getCharacterByIdUseCase.execute(1)

        assertThat(result).isInstanceOf(Result.Error::class)
        assertThat((result as Result.Error<Character, MortyException>).error).isInstanceOf(CharacterQueryException.QuerySingleCharacterException::class)

        coVerify {
            charactersRepository.getCharacterById(idSlot.captured)
        }

        confirmVerified(charactersRepository)
    }


}