package com.example.user.simplehub.api

import com.example.user.simplehub.api.model.*
import retrofit2.Call
import retrofit2.http.*

interface UserApi {

    @GET("user")
    @Headers("Accept: application/json")
    fun getUserInfo(): Call<GithubProfile>

    @GET("users/{username}/repos?type=all")
    @Headers("Accept: application/json")
    fun getRepoInfo(@Path("username") userName: String): Call<List<GithubRepo>>

    @GET("users/{username}/followers")
    @Headers("Accept: application/json")
    fun getFollowerInfo(@Path("username") userName: String): Call<List<GithubFollowers>>

    @GET("user/following")
    @Headers("Accept: application/json")
    fun getAuthFollowingInfo(): Call<List<GithubFollowing>>

    @GET("users/{username}/following")
    @Headers("Accept: application/json")
    fun getFollowingInfo(@Path("username") userName: String): Call<List<GithubFollowing>>

    @GET("users/{username}/starred")
    @Headers("Accept: application/json")
    fun getStarredInfo(@Path("username") userName: String): Call<List<GithubStarring>>

    @GET("user/issues")
    @Headers("Accept: application/json")
    fun getIssue(@Query("filter") filter: String,
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

    @GET("repos/{owner}/{repoName}/issues")
    @Headers("Accept: application/json")
    fun getRepoPulls(@Path("owner") owner: String,
                     @Path("repoName") repoName: String,
                     @Query("filter") filter: String,
                     @Query("state") state: String): Call<List<GithubPulls>>

    @GET("repos/{owner}/{repoName}/contributors")
    @Headers("Accept: application/json")
    fun getRepoContributors(@Path("owner") owner: String,
                            @Path("repoName") repoName: String): Call<List<GithubRepoContributors>>

    @GET("repos/{owner}/{repoName}/commits")
    @Headers("Accept: application/json")
    fun getRepoCommits(@Path("owner") owner: String,
                       @Path("repoName") repoName: String): Call<List<GithubRepoCommits>>

    @GET("search/users")
    @Headers("Accept: application/json")
    fun getUsers(@Query("q") query: String?): Call<GithubUserItem>

    @GET("users/{userName}")
    @Headers("Accept: application/json")
    fun getUser(@Path("userName") userName: String): Call<GithubProfile>

    @DELETE("user/following/{username}")
    @Headers("Accept: application/json")
    fun deleteFollowing(@Path("username") userName: String): Call<GithubFollowing>

    @PUT("user/following/{username}")
    @Headers("Accept: application/json")
    fun putFollowing(@Path("username") userName: String): Call<GithubFollowing>

    @PUT("user/starred/{owner}/{repo}")
    fun putStarring(@Path("owner") userName: String,
                    @Path("repo") repoName: String): Call<GithubFollowing>

    @DELETE("user/starred/{owner}/{repo}")
    fun deleteStarring(@Path("owner") userName: String,
                       @Path("repo") repoName: String): Call<GithubFollowers>


}