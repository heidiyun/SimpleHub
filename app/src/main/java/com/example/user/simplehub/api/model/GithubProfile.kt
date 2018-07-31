package com.example.user.simplehub.api.model

import com.google.gson.annotations.SerializedName

data class GithubProfile(@field:SerializedName("name") val name: String,
                         @field:SerializedName("login") val login: String,
                         @field:SerializedName("email") val email: String,
                         @field:SerializedName("avatar_url") val avatarUrl: String)

data class GithubRepo(val name: String)

data class GithubFollowers(val login: String,
                           @field:SerializedName("avatar_url") val avatarUrl: String,
                           @field:SerializedName("html_url") val address: String)

data class GithubFollowing(val login: String,
                         @field:SerializedName("avatar_url") val avatarUrl: String,
                         @field:SerializedName("html_url") val address: String)

data class GithubStarring(@field:SerializedName("full_name") val fullName: String)

data class IssueRepo(@field:SerializedName("full_name") val fullName: String )

data class GithubIssue(@field:SerializedName("title") val title: String,
                       @field:SerializedName("closed_at") val closedDate: String,
                       @field:SerializedName("pulls_url") val pullUrl: String,
                       val repository: IssueRepo)



data class PullUser(val login: String)

data class PullHead(val ref: String)
//master to

data class PullBase(val ref: String,
                    val repo: PullBaseRepo)

// to master

data class PullBaseRepo(val name: String)
//repo name

data class GithubPulls(val number: Int,
                       val state: String,
                       val title: String,
                       val user: PullUser,
                       val head: PullHead,
                       val base: String
                       )

// number는 줄.
//