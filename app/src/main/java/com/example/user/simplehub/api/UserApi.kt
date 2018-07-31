package com.example.user.simplehub.api

import com.example.user.simplehub.api.model.*
import com.example.user.simplehub.issueFragment.CreatedOpen
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

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
    fun getStarredInfo() : Call<List<GithubStarring>>
}

interface IssueApi {
    @GET("user/issues")
    @Headers("Accept: application/json")
    fun getIssue(@Query("filter") filter: String,
                 @Query("state") state: String) : Call<List<GithubIssue>>
}
