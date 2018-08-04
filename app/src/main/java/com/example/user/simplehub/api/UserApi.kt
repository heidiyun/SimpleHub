package com.example.user.simplehub.api

import com.example.user.simplehub.api.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("user")
    @Headers("Accept: application/json")
    fun getUserInfo(): Call<GithubProfile>

    @GET("user/repos")
    @Headers("Accept: application/json")
    fun getRepoInfo(): Call<List<GithubRepo>>

    @GET("user/followers")
    @Headers("Accept: application/json")
    fun getFollowerInfo(): Call<List<GithubFollowers>>

    @GET("user/following")
    @Headers("Accept: application/json")
    fun getFollowingInfo(): Call<List<GithubFollowing>>

    @GET("user/starred")
    @Headers("Accept: application/json")
    fun getStarredInfo(): Call<List<GithubStarring>>

    @GET("user/issues")
    @Headers("Accept: application/json")
    fun getIssue(@Query("filter") filter: String,
                 @Query("state") state: String): Call<List<GithubIssue>>

    @GET("user/issues")
    @Headers("Accept: application/json")
    fun getIssuePulls(@Query("filter") filter: String,
                      @Query("state") state: String): Call<List<GithubPulls>>

    @GET("repos/{owner}/{repoName}/contents/{dirName}?ref=master")
    @Headers("Accept: application/json")
    fun getRepoContents(@Path("owner") owner: String,
                        @Path("repoName") repoName: String,
                        @Path("dirName") dirName: String): Call<List<GithubRepoContents>>

    @GET("repos/{owner}/{repoName}/contents/{dirName}?ref=master")
    @Headers("Accept: application/json")
    fun getDirContents(@Path("owner") owner: String,
                       @Path("repoName") repoName: String,
                       @Path("dirName") dirName: String): Call<GithubRepoContents>



}



