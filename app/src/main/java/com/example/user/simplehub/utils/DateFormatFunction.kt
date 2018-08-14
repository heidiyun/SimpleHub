package com.example.user.simplehub.utils

import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault())


fun getSimpleDate(date: String, dateFormat: SimpleDateFormat): String {
    val timeZone = TimeZone.getTimeZone("Africa/Casablanca")
    val splitDate = date.replace("Z", ".000" + timeZone.displayName)
    println(splitDate)
    val givenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    val parsedDate = givenDateFormat.parse(splitDate)
//        val simpleDate = SimpleDateFormat("EEE, MMM d, HH:mm", Locale.getDefault())
    return dateFormat.format(parsedDate)
}