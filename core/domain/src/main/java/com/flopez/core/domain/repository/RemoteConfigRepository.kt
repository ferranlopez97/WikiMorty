package com.flopez.core.domain.repository

interface RemoteConfigRepository : Initializable {
    suspend fun isFeatureEnabled(key: String): Boolean
}