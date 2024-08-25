package com.msayeh.githubusers.features.profile.domain.usecase

import com.msayeh.githubusers.features.profile.domain.repo.ProfileRepo
import javax.inject.Inject

class GetProfileUsecase @Inject constructor(private val profileRepo: ProfileRepo) {
    operator fun invoke(username: String) = profileRepo.getProfile(username)
}