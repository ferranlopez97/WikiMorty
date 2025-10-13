package com.flopez.core.data.repository

import android.util.Log
import com.flopez.core.domain.repository.RemoteConfigRepository
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import javax.inject.Inject

class RemoteConfigRepositoryImpl @Inject constructor() : RemoteConfigRepository {

    var isInitialized: Boolean = false

    override suspend fun isFeatureEnabled(key: String): Boolean {
        if (!isInitialized) {
            initialize()
        }

        return Firebase.remoteConfig.getBoolean(key)
    }

    override suspend fun initialize() {
        val remoteConfig = Firebase.remoteConfig

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isInitialized = true
                    val updated = task.result
                    Log.d("RemoteConfig", "Config params updated: $updated")
                } else {
                    Log.e("RemoteConfig", "Fetch failed")
                }
            }

    }
}