package com.msayeh.githubusers.features.profile.domain.entities

import com.google.gson.annotations.SerializedName

data class Repository(
    val name: String,
    @SerializedName("html_url")
    val url: String,
    val description: String?,
    val language: String?,
    @SerializedName("stargazers_count")
    val starsCount: Int,
)