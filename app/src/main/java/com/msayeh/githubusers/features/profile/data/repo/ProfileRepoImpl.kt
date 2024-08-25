package com.msayeh.githubusers.features.profile.data.repo

import com.msayeh.githubusers.core.util.Resource
import com.msayeh.githubusers.features.profile.data.datasource.ProfileService
import com.msayeh.githubusers.features.profile.domain.entities.Profile
import com.msayeh.githubusers.features.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepoImpl @Inject constructor(private val profileService: ProfileService) :
    ProfileRepo {
    override fun getProfile(username: String): Flow<Resource<Profile>> = flow {
        emit(Resource.Loading())
        val response = profileService.getProfile(username)
        if (response.isSuccessful) {
            val reposResponse = profileService.getRepos(username)
            val repos = if (reposResponse.isSuccessful) {
                reposResponse.body()!!
            } else {
                emptyList()
            }
            val profile = response.body()!!.copy(repositories = repos)
            emit(Resource.Success(profile))
        } else {
            emit(Resource.Error(Exception(response.message())))
        }
    }
}