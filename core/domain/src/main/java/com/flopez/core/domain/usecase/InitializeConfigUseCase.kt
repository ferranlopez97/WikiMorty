package com.flopez.core.domain.usecase

import com.flopez.core.domain.repository.RemoteConfigRepository

class InitializeConfigUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
){

    suspend fun execute() : Boolean {
        remoteConfigRepository.initialize()
        return true
    }
}