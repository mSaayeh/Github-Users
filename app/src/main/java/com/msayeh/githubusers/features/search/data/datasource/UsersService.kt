package com.msayeh.githubusers.features.search.data.datasource

import com.msayeh.githubusers.features.search.data.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String, @Query("page") page: Int): Response<SearchResponse>
}