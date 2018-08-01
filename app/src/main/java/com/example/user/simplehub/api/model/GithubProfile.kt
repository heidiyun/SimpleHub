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

data class GithubStarring(@field:SerializedName("full_name") val fullName: String,
                          @field:SerializedName("stargazers_count") val starNumber: Int)

data class IssueRepo(@field:SerializedName("full_name") val fullName: String)

data class GithubIssue(@field:SerializedName("title") val title: String,
                       @field:SerializedName("closed_at") val closedDate: String,
                       @field:SerializedName("pulls_url") val pullUrl: String,
                       val repository: IssueRepo)

data class PullsUser(val login: String)

data class PullsRepo(@field:SerializedName("full_name") val repoName: String)

data class Pulls(val url: String)

data class GithubPulls(@field:SerializedName("pull_request") val pullRequest: Pulls?,
                       val title: String,
                       val number: Int,
                       val user: PullsUser,
                       @field:SerializedName("created_at") val PullsDate: String,
                       val repository: PullsRepo
)