package com.msayeh.githubusers.features.search.domain.entities

import com.google.gson.annotations.SerializedName

data class User(
    // This annotation is not architecturally significant for the purpose of this exercise
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
)
