package com.msayeh.githubusers.features.profile.presentation

import com.msayeh.githubusers.features.profile.domain.entities.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val wantedUrl: String? = null,
    val navigateBack: Boolean = false,
)