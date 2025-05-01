package com.flopez.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.flopez.core.presentation.reducer.Reducer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow


//Comentar uso del reducer
abstract class BaseViewModel<State: Reducer.ViewState, Intent: Reducer.ViewIntent, Effect: Reducer.ViewEffect>(
    initialState: State,
//    private val reducer: Reducer<State, Intent, Effect>
) : ViewModel(){

    protected val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    open val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _effect = Channel<Effect>()
    val effect : Flow<Effect>
        get() = _effect.receiveAsFlow()

    fun sendEffect(effect: Effect){
        val sent = _effect.trySend(effect)
    }

    abstract fun handleIntent(intent: Intent)
}