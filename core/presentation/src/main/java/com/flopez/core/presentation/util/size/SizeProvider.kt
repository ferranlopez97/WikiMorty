package com.flopez.core.presentation.util.size

import androidx.compose.ui.unit.Dp


/**
 * SizeProvider se encarga de proporcionar diferentes valores por defecto para usar en composables.
 * La implementación dependera del tamaño del dispositivo, usando la api de material3-adaptive.
 * Para esta prueba solo implementare Compact
 */
interface SizeProvider {
    val defaultPadding: Dp
    val defaultSpacing: Dp

    val characterListPadding: Dp
    val characterListSpacing: Dp

    val characterListImageSize: Dp
    val characterDetailImageSize: Dp

    val characterSummaryPadding: Dp
}