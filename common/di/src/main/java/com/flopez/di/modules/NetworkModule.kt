package com.flopez.di.modules

import com.flopez.core.network.NetworkConstants
import com.flopez.core.network.datasource.CharactersRemoteDataSource
import com.flopez.core.network.datasource.CharactersRemoteDataSourceImpl
import com.flopez.core.network.service.CharactersRetrofitService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun baseURL() : String = NetworkConstants.BASE_URL

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun retrofit(baseURL: String) : Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }


    @Provides
    @Singleton
    fun characterApiService(retrofit: Retrofit) : CharactersRetrofitService {
        return retrofit.create(CharactersRetrofitService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkAbstractModule {

    @Binds
    abstract fun charactersRemoteDataSource(charactersRemoteDataSourceImpl: CharactersRemoteDataSourceImpl) : CharactersRemoteDataSource

}