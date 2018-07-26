package com.example.user.simplehub.api

import com.example.user.simplehub.api.model.*
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

interface FollowerApi {
    @GET("user/followers")
    @Headers("Accept: application/json")
    fun getFollowerInfo() : Call<List<GithubFollowers>>
}

interface FollowingApi {
    @GET("user/following")
    @Headers("Accept: application/json")
    fun getFollowerInfo() : Call<List<GithubFollowing>>
}

interface StarringApi {
    @GET("user/starred")
    @Headers("Accept: application/json")
    fun getFollowerInfo() : Call<List<GithubStarred>>
}