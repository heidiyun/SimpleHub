package com.example.user.simplehub.api

import android.content.Context
import com.example.user.simplehub.api.model.GithubProfile
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


val gson2 = GsonBuilder()
        .setLenient()
        .create()!!

val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val httpClient: OkHttpClient = OkHttpClient.Builder().apply {
    addInterceptor(loggingInterceptor)
}.build()

val authApi: AuthApi = Retrofit.Builder().apply {
    baseUrl("https://github.com/")
    client(httpClient)
    addConverterFactory(GsonConverterFactory.create(gson2))
}.build().create(AuthApi::class.java)

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder().apply {
            getToken(context)?.let {
                token -> addHeader("Authorization", "bearer $token")
            }
        }.build()

        return chain.proceed(request)
    }

}fun authHttpClient(context: Context) = OkHttpClient.Builder().apply {
    addInterceptor(AuthInterceptor(context))
}.build()

fun provideUserApi(context: Context) = Retrofit.Builder().apply {
    baseUrl("https://api.github.com/")
    client(authHttpClient(context))
    addConverterFactory(GsonConverterFactory.create(gson2))
}.build().create(UserApi::class.java)
