package com.flopez.core.network.service

import com.flopez.core.network.model.CharacterQueryResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersRetrofitService {

    @GET("character")
    suspend fun getPagedCharacters(@Query("page") page: Int, @Query("name") name: String): CharacterQueryResponseDTO

}