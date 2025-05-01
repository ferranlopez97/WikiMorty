package com.flopez.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

fun Date.toStringFormat(format: String = "dd/MM/yyyy") : String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(this)
}

fun String.toDate(format: String = DATE_FORMAT) : Date {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.parse(this)
}