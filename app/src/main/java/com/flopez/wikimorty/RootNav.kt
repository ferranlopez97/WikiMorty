package com.flopez.wikimorty

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.flopez.core.presentation.util.size.CompactSizeProvider
import com.flopez.core.presentation.util.size.LocalSizeProvider
import com.flopez.wikimorty.navigation.CharactersGraph
import com.flopez.wikimorty.navigation.animation.NavHostAnimations
import com.flopez.wikimorty.navigation.graph.charactersGraph


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootNav() {

    val navController = rememberNavController()

    CompositionLocalProvider(LocalSizeProvider provides CompactSizeProvider()) {
        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = CharactersGraph,
                enterTransition = NavHostAnimations.enterTransition,
                exitTransition = NavHostAnimations.exitTransition,
                popEnterTransition = NavHostAnimations.popEnterTransition,
                popExitTransition = NavHostAnimations.popExitTransition
            ) {
                charactersGraph(navController, this@SharedTransitionLayout)

            }
        }
    }
}