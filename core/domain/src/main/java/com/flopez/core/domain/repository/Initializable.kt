package com.flopez.core.domain.repository

interface Initializable {
    suspend fun initialize()
}