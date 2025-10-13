package com.flopez.core.domain.feature.remoteconfig

import com.flopez.core.domain.repository.RemoteConfigRepository

class IsFeatureEnabledUseCase(
    private val remoteConfigRepository: RemoteConfigRepository
) {
    suspend fun execute(remoteFeatureKey: RemoteConfigKey): Boolean {
        return remoteConfigRepository.isFeatureEnabled(remoteFeatureKey.key)
    }
}