package com.flopez.core.data.usecase

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import com.flopez.core.data.util.loadJsonFromAssets
import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.result.Result
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase
import com.flopez.di.modules.DatabaseModule
import com.flopez.di.modules.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(DatabaseModule::class, NetworkModule::class)
class QueryPagedCharactersUseCaseIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var queryPagedCharactersUseCase: QueryPagedCharactersUseCase

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
    fun shouldInsertCharactersIntoRoomIfUseCaseSuccess() = runTest {

        val mockResponse = loadJsonFromAssets("characters_response_page1.json")
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(mockResponse)
        )

        val result = queryPagedCharactersUseCase.execute()

        assertThat(result).isInstanceOf(Result.Success::class)

        val storedCharacters = charactersLocalDataSource.getAllCharacters().first()

        assertThat(storedCharacters).isNotEmpty()
        assertThat(storedCharacters).hasSize(20)
    }

    @Test
    fun shouldNotInsertIfRemoteFails() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val result = queryPagedCharactersUseCase.execute()
        val characters = charactersLocalDataSource.getAllCharacters().first()

        assertThat(result).isInstanceOf(Result.Error::class)
        assertThat((result as Result.Error).error).isEqualTo(CharacterQueryException.QueryCharactersListException)
        assertThat(characters).isEmpty()
    }

    @Test
    fun shouldNotInsertIfRemoteReturnsEmptyList() = runTest {
        val emptyResponse = loadJsonFromAssets("characters_empty_response.json")
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(emptyResponse))

        val result = queryPagedCharactersUseCase.execute()

        assertThat(result).isInstanceOf(Result.Success::class)
        val stored = charactersLocalDataSource.getAllCharacters().first()
        assertThat(stored).isEmpty()
    }


}