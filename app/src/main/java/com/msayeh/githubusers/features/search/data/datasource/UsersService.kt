package com.msayeh.githubusers.features.search.data.datasource

import com.msayeh.githubusers.features.search.domain.entities.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String, @Query("page") page: Int): Response<List<User>>
}