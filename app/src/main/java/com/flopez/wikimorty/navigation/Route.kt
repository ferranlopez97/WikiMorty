package com.flopez.wikimorty.navigation

import kotlinx.serialization.Serializable

sealed interface Route

@Serializable
data object CharactersGraph : Route

@Serializable
data object CharactersListRoute: Route

@Serializable
data class CharacterDetailRoute(val id: Long) : Route