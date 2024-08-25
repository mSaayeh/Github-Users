package com.msayeh.githubusers.features.search.domain.repo

import com.msayeh.githubusers.core.util.Resource
import com.msayeh.githubusers.features.search.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepo {
    fun searchUsers(query: String, page: Int, limit: Int): Flow<Resource<List<User>>>
}