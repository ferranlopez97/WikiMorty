package com.flopez.core.presentation.reducer


//Comentar
interface Reducer<State : Reducer.ViewState, Event : Reducer.ViewIntent, Effect : Reducer.ViewEffect> {

    interface ViewState

    interface ViewIntent

    interface ViewEffect

    fun reduce(previousState: State, event: Event): Pair<State, Effect?>
}