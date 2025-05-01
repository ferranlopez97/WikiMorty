package com.flopez.core.data.util

fun loadJsonFromAssets(fileName: String): String {
    val inputStream = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}