package com.msayeh.githubusers.features.search.data.repo

import com.msayeh.githubusers.core.util.Resource
import com.msayeh.githubusers.features.search.data.datasource.UsersService
import com.msayeh.githubusers.features.search.domain.entities.User
import com.msayeh.githubusers.features.search.domain.repo.UsersRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(private val usersService: UsersService): UsersRepo {
    override fun searchUsers(query: String, page: Int): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val response = usersService.searchUsers(query, page)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!.items))
            } else {
                emit(Resource.Error(HttpException(response)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}