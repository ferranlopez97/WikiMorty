package com.flopez.core.presentation.util.list

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.flopez.core.presentation.util.constants.LOAD_MORE_CHARACTERS_THRESHOLD
import kotlinx.coroutines.flow.distinctUntilChanged


/**
 * Observa el indice de una lazyList y llama el callback cuando llega al offset indicado
 */
@Composable
fun LoadMoreCharactersEffect(
    listState: LazyListState,
    offset: Int = LOAD_MORE_CHARACTERS_THRESHOLD,
    onEndReached : () -> Unit
) {

    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - offset)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onEndReached()
            }
    }
}