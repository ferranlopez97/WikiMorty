package com.flopez.core.data.repository

import android.util.Log
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.flopez.core.data.fake.CharactersLocalDataSourceFake
import com.flopez.core.data.generators.characterDTO
import com.flopez.core.data.generators.characterWithLocations
import com.flopez.core.data.generators.queryResponseDto
import com.flopez.core.data.mapper.toDomain
import com.flopez.core.data.mapper.toEntity
import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.database.datasource.location.LocationsLocalDataSource
import com.flopez.core.database.entity.LocationEntity
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.network.datasource.CharactersRemoteDataSource
import com.flopez.core.network.model.CharacterQueryInfoDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class CharactersRepositoryImplTest {

    private lateinit var charactersRepository: CharactersRepository
    private lateinit var remoteDataSource: CharactersRemoteDataSource
    private lateinit var localDataSource: CharactersLocalDataSource
    private lateinit var locationLocalDataSource: LocationsLocalDataSource


    @BeforeEach
    fun setup() {
        mockkStatic(android.util.Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.v(any(), any()) } returns 0

        remoteDataSource = mockk()
        localDataSource = CharactersLocalDataSourceFake()
        locationLocalDataSource = mockk(relaxed = true)
        charactersRepository = CharactersRepositoryImpl(
            charactersRemoteDataSource = remoteDataSource,
            charactersLocalDataSource = localDataSource,
            locationLocalDataSource = locationLocalDataSource
        )
    }

    @Test
    fun `loadNextPage should return END_REACHED if end of pagination reached`() = runTest {

        coEvery {  remoteDataSource.fetchCharacters(any(), any()) } returns queryResponseDto()

        //Para llegar al final de la paginación
        val firstResult = charactersRepository.loadNextPage()

        val secondResult = charactersRepository.loadNextPage()

        assertThat(firstResult).isEqualTo(PageResult.ITEMS_AVAILABLE)
        assertThat(secondResult).isEqualTo(PageResult.END_REACHED)
        assertThat(localDataSource.countCharacters()).isEqualTo(20)

        coVerify(exactly = 1) {
            remoteDataSource.fetchCharacters(1, "")
        }

        coVerify(exactly = 0) {
            remoteDataSource.fetchCharacters(2, any())
        }

        confirmVerified(remoteDataSource)
    }



    @Test
    fun `loadNextPage should insert results into local cache`() = runTest {
        coEvery {  remoteDataSource.fetchCharacters(any(), any()) } returns queryResponseDto()

        val charactersFromRemote = queryResponseDto().characters.size

        val firstResult = charactersRepository.loadNextPage()

        assertThat(firstResult).isEqualTo(PageResult.ITEMS_AVAILABLE)

        assertThat(localDataSource.getAllCharacters().first()).hasSize(charactersFromRemote)

        coVerify {
            remoteDataSource.fetchCharacters(1, "")
        }

        coVerify(
            exactly = charactersFromRemote * 2 //2 locations por personaje
        ) {
            locationLocalDataSource.insert(any<LocationEntity>())
        }

        confirmVerified(remoteDataSource, locationLocalDataSource)
    }

    @Test
    fun `loadNextPage should increment page number on each call`() = runTest {
        coEvery { remoteDataSource.fetchCharacters(any(), any()) } returns queryResponseDto().copy(
            characterQueryInfoDTO = CharacterQueryInfoDTO(
                count = 300,
                pages = 4,
                next = null,
                prev = null
            )
        )

        charactersRepository.loadNextPage()
        charactersRepository.loadNextPage()

        coVerify {
            remoteDataSource.fetchCharacters(1, "")
            remoteDataSource.fetchCharacters(2, "")
        }
    }


    @Test
    fun `loadNextPage should not call local cache if response is empty and return NO_ITEMS_AVAILABLE`() = runTest {
        coEvery {  remoteDataSource.fetchCharacters(any(), any()) } returns queryResponseDto().copy(
            characters = emptyList()
        )

        val firstResult = charactersRepository.loadNextPage()

        assertThat(firstResult).isEqualTo(PageResult.NO_ITEMS_AVAILABLE)
        assertThat(localDataSource.getAllCharacters().first()).isEmpty()
        assertThat(localDataSource.countCharacters()).isEqualTo(0)

        coVerify {
            remoteDataSource.fetchCharacters(1, "")
        }

        coVerify (exactly = 0){
            locationLocalDataSource.insert(any<LocationEntity>())
        }

        confirmVerified(remoteDataSource, locationLocalDataSource)
    }


    @Test
    fun `getAllCharacters should emit mapped characters`() = runTest {
        val fakeEntity = characterDTO()
        val expected = fakeEntity.toEntity().characterWithLocations().toDomain()

        val list = (1 .. 10).map {
            fakeEntity.toEntity()
        }

        localDataSource.insertAll(list)

        charactersRepository.getAllCharacters().test {
            val result = awaitItem()

            assertThat(result).hasSize(10)
            assertThat(result.first()).isEqualTo(expected)

            cancelAndIgnoreRemainingEvents()
        }
    }

}