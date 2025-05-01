package com.flopez.core.presentation.screens.characters.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flopez.core.presentation.util.size.LocalSizeProvider

@Composable
fun CharacterLoadError(
    errorMessage: String,
    onOkayClicked: () -> Unit
) {

    val sizeProvider = LocalSizeProvider.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(sizeProvider.defaultSpacing))

        Button(
            onClick = onOkayClicked,
            shape = ShapeDefaults.Medium
        ) {
            Text(
                text = "Volver atras",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}