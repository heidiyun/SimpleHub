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

data class GithubStarred(@field:SerializedName("full_name") val fullName: String)