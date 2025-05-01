package com.flopez.core.presentation.screens.characters.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flopez.core.presentation.util.string.capitalize

@Composable
fun CharacterAttribute(
    modifier: Modifier = Modifier,
    attributeTitle: String,
    attributeContent: @Composable () -> Unit
) {
    Column {
        Text(attributeTitle.capitalize(), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            attributeContent()
        }
        Spacer(Modifier.height(10.dp))
    }

}

@Composable
fun CharacterAttributeText(
    text: String
) {
    Text(
        text = text.capitalize(),
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview
@Composable
private fun CharacterAttributePreview() {
    CharacterAttribute(
        attributeTitle = "ubicación",
        attributeContent = {
            CharacterAttributeText("Barcelona")
        }
    )
}