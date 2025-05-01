package com.flopez.di.test_modules

import com.flopez.core.network.service.CharactersRetrofitService
import com.flopez.di.modules.NetworkModule
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object NetworkTestModule {

    @Provides
    @Singleton
    fun mockWebServer() : MockWebServer {
        return MockWebServer().apply {
            start()
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideMockCharactersApi(
        mockWebServer: MockWebServer
    ): CharactersRetrofitService {
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(CharactersRetrofitService::class.java)
    }
}