package com.example.user.simplehub.api.model

import com.google.gson.annotations.SerializedName

data class GithubProfile(@field:SerializedName("name") val name: String,
                         @field:SerializedName("login") val login: String,
                         @field:SerializedName("email") val email: String,
                         @field:SerializedName("avatar_url") val avatarUrl: String)

data class RepoOwner(val login: String)

data class GithubRepo(val name: String,
                      val owner: RepoOwner,
                      val language: String,
                      @field:SerializedName("watchers_count") val watcherCount: Int,
                      @field:SerializedName("stargazers_count") val starCount: Int,
                      @field:SerializedName("full_name") val fullName: String)

data class GithubFollowers(val login: String,
                           @field:SerializedName("avatar_url") val avatarUrl: String,
                           @field:SerializedName("html_url") val address: String)

data class GithubFollowing(val login: String,
                           @field:SerializedName("avatar_url") val avatarUrl: String,
                           @field:SerializedName("html_url") val address: String)

data class GithubStarring(@field:SerializedName("full_name") val fullName: String,
                          @field:SerializedName("stargazers_count") val starNumber: Int,
                          val language: String,
                          val name: String,
                          val owner: RepoOwner)

data class PullsUser(val login: String)

data class PullsRepo(@field:SerializedName("full_name") val repoName: String)

data class Pulls(val url: String)

data class GithubPulls(@field:SerializedName("pull_request") val pullRequest: Pulls?,
                       @field:SerializedName("created_at") val PullsDate: String,
                       val title: String,
                       val number: Int,
                       val user: PullsUser,
                       val repository: PullsRepo)

data class GithubRepoContents(val name: String, val type: String,
                              @field:SerializedName("download_url") val url: String)

data class GithubRepoContributors(val login: String,
                                  @field:SerializedName("avatar_url") val avatarUrl: String,
                                  val contributions: Int)

data class Committer(val login: String, val avatar_url: String)

data class CommitCommitter(val date: String)

data class Commit(val message: String, val committer: CommitCommitter)

data class GithubRepoCommits(val commit: Commit, val committer: Committer)

data class GithubUsers(val login: String,
                       @field:SerializedName("avatar_url") val avatarUrl: String)

data class GithubUserItem(@field:SerializedName("total_count") val totalCount: Int,
                          val items: List<GithubUsers>)

data class GithubSubscription(val subscribed: Boolean, val message: String)

data class GithubSubscribers(val login: String)
