package com.msayeh.githubusers.features.search.data.datasource

import com.msayeh.githubusers.core.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): Response<SearchResponse>
}