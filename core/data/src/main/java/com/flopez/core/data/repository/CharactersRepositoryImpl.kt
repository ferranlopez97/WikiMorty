package com.flopez.core.data.repository

import android.util.Log
import com.flopez.core.data.mapper.toDomain
import com.flopez.core.data.mapper.toEntity
import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.database.datasource.location.LocationsLocalDataSource
import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.pagination.PagedDataState
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.network.datasource.CharactersRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CharactersRepositoryImpl @Inject constructor(
    private val charactersRemoteDataSource: CharactersRemoteDataSource,
    private val charactersLocalDataSource: CharactersLocalDataSource,
    private val locationLocalDataSource: LocationsLocalDataSource,
) : CharactersRepository {

    private val TAG = CharactersRepositoryImpl::class.simpleName

    private val PAGE_SIZE = 20
    private var pagedState: PagedDataState = PagedDataState.empty(PAGE_SIZE)


    /**
     * Carga la siguiente pagina de personajes
     * @param filter Filtros que se aplicaran a la query remota.
     * @return [PageResult.END_REACHED] si se llega al final de la paginación o
     * [PageResult.ITEMS_AVAILABLE] si hay siguiente pagina y se descarga correctamente
     */
    override suspend fun loadNextPage(filter: QueryFilter?) : PageResult {

        //Si no hay más paginas return NO_MORE_ITEMS_AVAILABLE
        if (!pagedState.hasNextPage) {
            Log.d(TAG, "loadMorePages: No more pages to load")
            return PageResult.END_REACHED
        }

        //Como el tamaño de la pagina es fijo, podemos calcular la siguiente pagina en base al número
        //de persojas ya guardados en BBDD. Comentar mejora
        val totalCharacters = charactersLocalDataSource.countCharacters()
        pagedState.currentPage = (totalCharacters / pagedState.pageSize) + 1


        val response = charactersRemoteDataSource.fetchCharacters(pagedState.currentPage)

        Log.d(TAG, "loadMorePages: response = ${response.characterQueryInfoDTO}")

        /*Si la pagina actual es inferior al numero total de paginas, hay siguiente página
        * Tambien se podria mirar next es != null */
        pagedState.hasNextPage = pagedState.currentPage.toLong() < response.characterQueryInfoDTO.pages

        Log.d(TAG, "loadMorePages: currentPage=${pagedState.currentPage}")

        //Guardar locations primero y luego personaje
        if (response.characters.isEmpty()) {
            return PageResult.NO_ITEMS_AVAILABLE
        }

        //Guardar primero locations, luego personaje
        response.characters.forEach {
            locationLocalDataSource.insert(it.location.toEntity())
            locationLocalDataSource.insert(it.origin.toEntity())
        }

        charactersLocalDataSource.insertAll(characters = response.characters.map { it.toEntity() })

        return PageResult.ITEMS_AVAILABLE
    }


    /**
     * Devuelve flow con todos los personajes guardados en cache local
     * Añadir más filtros si fuera necesario
     */
    override fun getAllCharacters(filter: QueryFilter?): Flow<List<Character>> {

        val flow = when (filter) {
            is QueryFilter.ByName -> charactersLocalDataSource.getCharactersByName(filter.name)
            else -> charactersLocalDataSource.getAllCharacters()
        }

        return flow.map { characters ->
            characters.map { characterEntity ->
                characterEntity.toDomain()
            }
        }
    }


    override fun getCharacterById(id: Long): Character? {
        return charactersLocalDataSource.getById(id)?.toDomain()
    }

}