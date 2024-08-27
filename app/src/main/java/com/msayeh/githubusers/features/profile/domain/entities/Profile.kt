package com.msayeh.githubusers.features.profile.domain.entities

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val url: String,
    val followers: Int,
    val following: Int,
    val bio: String?,
    @SerializedName("public_repos")
    val publicReposCount: Int,
    val name: String?,
    val repositories: List<Repository>?
)