package com.example.user.simplehub.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*




fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit, failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>?, t: Throwable) = failure(t)

        override fun onResponse(call: Call<T>, response: Response<T>) = success(response)

    })
}
