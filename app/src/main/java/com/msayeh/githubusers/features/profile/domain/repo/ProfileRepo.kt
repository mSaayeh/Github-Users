package com.msayeh.githubusers.features.profile.domain.repo

import com.msayeh.githubusers.core.util.Resource
import com.msayeh.githubusers.features.profile.domain.entities.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    fun getProfile(username: String): Flow<Resource<Profile>>
}