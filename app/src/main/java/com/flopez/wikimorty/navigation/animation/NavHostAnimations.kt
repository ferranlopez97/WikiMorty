package com.flopez.wikimorty.navigation.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry


object NavHostAnimations {
    val enterTransition :  (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)= { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(200)) }
    val exitTransition :  (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(200)) }
    val popEnterTransition:  (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(200)) }
    val popExitTransition:  (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(200)) }
}
