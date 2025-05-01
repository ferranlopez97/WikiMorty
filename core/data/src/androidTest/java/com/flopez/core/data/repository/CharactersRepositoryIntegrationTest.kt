package com.flopez.core.data.repository

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.flopez.core.data.generators.characterEntity
import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.di.modules.DatabaseModule
import com.flopez.di.modules.NetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(DatabaseModule::class, NetworkModule::class)
class CharactersRepositoryIntegrationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var charactersLocalDataSource: CharactersLocalDataSource

    @Inject
    lateinit var charactersRepository: CharactersRepository

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
    fun shouldReturnFilteredCharactersIfFilterIsNotNull() = runTest {

        val characters = listOf(
            characterEntity(1, "Rick"),
            characterEntity(2, "Morty"),
            characterEntity(3, "Summer")
        )

        val filter = QueryFilter.ByName("Rick")

        charactersLocalDataSource.insertAll(characters)

        val result = charactersRepository.getAllCharacters(filter).first()
        assertThat(result).hasSize(1)
    }

    @Test
    fun shouldReturnCharacterByIdIfExistsInRoom() = runTest {

        val characters = listOf(
            characterEntity(1, "Rick"),
            characterEntity(2, "Morty"),
            characterEntity(3, "Summer")
        )

        charactersLocalDataSource.insertAll(characters)

        val result = charactersRepository.getCharacterById(3)
        assertThat(result).isNotNull()
        assertThat(result?.name).isEqualTo(characters[2].name)
    }

    @Test
    fun shouldReturnNullIfCharacterByIdDoesNotExistInRoom() = runTest {

        val characters = listOf(
            characterEntity(1, "Rick"),
            characterEntity(2, "Morty"),
            characterEntity(3, "Summer")
        )

        charactersLocalDataSource.insertAll(characters)

        val result = charactersRepository.getCharacterById(4)
        assertThat(result).isNull()
    }

}