package com.flopez.core.network.datasource

import com.flopez.core.network.model.CharacterQueryResponseDTO
import com.flopez.core.network.service.CharactersRetrofitService
import javax.inject.Inject

class CharactersRemoteDataSourceImpl @Inject constructor(
    private val charactersRetrofitService: CharactersRetrofitService
) : CharactersRemoteDataSource {

    override suspend fun fetchCharacters(page: Int, filterName: String) : CharacterQueryResponseDTO = charactersRetrofitService.getPagedCharacters(page, filterName)

}