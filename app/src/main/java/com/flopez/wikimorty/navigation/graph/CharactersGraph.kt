package com.flopez.wikimorty.navigation.graph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.flopez.core.presentation.screens.characters.detail.CharacterDetailScreen
import com.flopez.core.presentation.screens.characters.list.CharacterListScreen
import com.flopez.wikimorty.navigation.CharacterDetailRoute
import com.flopez.wikimorty.navigation.CharactersGraph
import com.flopez.wikimorty.navigation.CharactersListRoute


@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.charactersGraph(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope
) {

    with(sharedTransitionScope) {
        navigation<CharactersGraph>(
            startDestination = CharactersListRoute
        ) {
            composable<CharactersListRoute>{
                CharacterListScreen(
                    animatedVisibilityScope = this,
                ) {
                    navController.navigate(CharacterDetailRoute(it.id))
                }
            }

            composable<CharacterDetailRoute> {
                CharacterDetailScreen(
                    animatedVisibilityScope = this
                ){
                    navController.navigateUp()
                }
            }

        }
    }




}