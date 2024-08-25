package com.msayeh.githubusers.features.profile.data.datasource

import com.msayeh.githubusers.features.profile.domain.entities.Profile
import com.msayeh.githubusers.features.profile.domain.entities.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("users/{username}")
    suspend fun getProfile(@Path("username") username: String): Response<Profile>

    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): Response<List<Repository>>
}