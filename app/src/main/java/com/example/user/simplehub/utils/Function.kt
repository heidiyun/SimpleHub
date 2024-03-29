package com.example.user.simplehub.utils

import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault())

fun getSimpleDate(date: String?, dateFormat: SimpleDateFormat): String {
    val timeZone = TimeZone.getTimeZone("Africa/Casablanca")
    val splitDate = date?.replace("Z", ".000" + timeZone.displayName)
    val givenDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    val parsedDate = givenDateFormat.parse(splitDate)
    return dateFormat.format(parsedDate)
}

fun getAssetJsonDate(context: Context): String? {
    val json: String?
    try {
        val inputStream = context.assets.open("color.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return json
}

@GlideModule
class AppGlideModule : AppGlideModule()