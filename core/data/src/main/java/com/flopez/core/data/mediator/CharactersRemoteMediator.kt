//package com.flopez.core.data.mediator
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import com.flopez.core.data.mapper.toDomain
//import com.flopez.core.data.mapper.toEntity
//import com.flopez.core.database.datasource.character.CharactersLocalDataSource
//import com.flopez.core.database.entity.CharacterEntity
//import com.flopez.core.domain.model.CharacterQueryInfo
//import com.flopez.core.network.datasource.CharactersRemoteDataSource
//import javax.inject.Inject
//
//@OptIn(ExperimentalPagingApi::class)
//class CharactersRemoteMediator @Inject constructor(
//    private val remoteDataSource: CharactersRemoteDataSource,
//    private val localDataSource: CharactersLocalDataSource
//) : RemoteMediator<Int, CharacterEntity>() {
//
//    private var currentPage = 1
//    private var lastQueryInfo: CharacterQueryInfo? = null
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, CharacterEntity>
//    ): MediatorResult {
//        return try {
//            val page = when (loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//
//                    //Comprobar si es ultima pagina
//                    val isLastPage = lastQueryInfo?.next == null
//
//                    if (isLastPage) {
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//
//                    currentPage++
//                }
//            }
//
//            val response = remoteDataSource.fetchCharacters(page, "")
//
//            lastQueryInfo = response.characterQueryInfoDTO.toDomain()
//
//            if (loadType == LoadType.REFRESH ) {
//                localDataSource.clearAll()
//            }
//
//            localDataSource.insertAll(response.characters.map {it.toEntity()})
//
//            MediatorResult.Success(endOfPaginationReached = response.characters.isEmpty())
//        } catch (exception: Exception) {
//            MediatorResult.Error(exception)
//        }
//    }
//}