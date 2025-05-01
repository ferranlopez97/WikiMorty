package com.flopez.core.presentation.util.size

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class CompactSizeProvider : SizeProvider {

    override val defaultPadding: Dp
        get() = 16.dp

    override val defaultSpacing: Dp
        get() = 16.dp
    override val characterListPadding: Dp
        get() = 10.dp
    override val characterListSpacing: Dp
        get() = 10.dp
    override val characterListImageSize: Dp
        get() = 100.dp
    override val characterDetailImageSize: Dp
        get() = 250.dp
    override val characterSummaryPadding: Dp
        get() = 8.dp


}