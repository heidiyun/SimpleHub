package com.example.user.simplehub.api.model

import com.google.gson.annotations.SerializedName

data class GithubAuth(@field:SerializedName("access_token") val accessToken: String,
                      @field:SerializedName("token_type") val tokenType: String)