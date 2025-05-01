package com.flopez.core.network.datasource

import com.flopez.core.network.model.CharacterQueryResponseDTO

interface CharactersRemoteDataSource {

    suspend fun fetchCharacters(page: Int, filterName: String = "") : CharacterQueryResponseDTO

}