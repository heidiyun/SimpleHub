package com.example.user.simplehub.api

import com.example.user.simplehub.api.model.GithubProfile
import com.example.user.simplehub.api.model.GithubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {
    @GET("user")
    @Headers("Accept: application/json")
    fun getUserInfo(): Call<GithubProfile>
}

interface RepoApi {
    @GET("user/repos")
    @Headers("Accept: application/json")
    fun getUserInfo(): Call<List<GithubRepo>>
}