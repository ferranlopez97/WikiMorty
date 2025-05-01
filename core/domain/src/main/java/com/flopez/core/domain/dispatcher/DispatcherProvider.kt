package com.flopez.core.domain.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Clase para poder cambiar de dispatchers para tests.
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}