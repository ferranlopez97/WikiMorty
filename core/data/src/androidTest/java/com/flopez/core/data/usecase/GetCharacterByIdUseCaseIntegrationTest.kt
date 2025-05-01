package com.flopez.core.data.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.flopez.core.data.generators.characterEntity
import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.result.Result
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.di.modules.DatabaseModule
import com.flopez.di.modules.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(DatabaseModule::class, NetworkModule::class)
class GetCharacterByIdUseCaseIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @Inject
    lateinit var charactersLocalDataSource: CharactersLocalDataSource

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldReturnErrorIfCharacterDoesNotExistInRoom() = runTest {

        //no hace falta preparar nada, in-memory db esta vacía

        val result = getCharacterByIdUseCase.execute(34)

        assertThat(result).isInstanceOf(Result.Error::class)
        assertThat((result as Result.Error).error).isEqualTo(CharacterQueryException.QuerySingleCharacterException(34))

    }

    @Test
    fun shouldReturnSuccessWithCharacterIfExistInRoom() = runTest {

        val characters = listOf(
            characterEntity(1, "Rick"),
            characterEntity(2, "Morty"),
            characterEntity(3, "Summer")
        )

        charactersLocalDataSource.insertAll(characters)

        val result = getCharacterByIdUseCase.execute(2)

        assertThat(result).isInstanceOf(Result.Success::class)
        assertThat((result as Result.Success).data.name).isEqualTo(characters[1].name)
    }
}