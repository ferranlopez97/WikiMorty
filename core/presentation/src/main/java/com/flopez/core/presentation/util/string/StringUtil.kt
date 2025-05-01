package com.flopez.core.presentation.util.string

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun String.capitalize() : String {
    return  this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(LocalConfiguration.current.locales[0]) else it.toString() }
}