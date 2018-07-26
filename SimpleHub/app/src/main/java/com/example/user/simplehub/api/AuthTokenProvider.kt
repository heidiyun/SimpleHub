package com.example.user.simplehub.api

import android.content.Context
import android.preference.PreferenceManager

const val KEY_AUTH_TOKEN = "user.example.github.auth_token"

fun updateToken(context: Context, token: String) {
    PreferenceManager.getDefaultSharedPreferences(context).edit()
            .putString(KEY_AUTH_TOKEN, token)
            .apply()
}

fun getToken(context: Context): String? {
    return PreferenceManager.getDefaultSharedPreferences(context)
            .getString(KEY_AUTH_TOKEN, null)
}