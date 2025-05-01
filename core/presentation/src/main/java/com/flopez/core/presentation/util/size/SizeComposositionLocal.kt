package com.flopez.core.presentation.util.size

import androidx.compose.runtime.compositionLocalOf


val LocalSizeProvider = compositionLocalOf<SizeProvider> { CompactSizeProvider() }