package com.msayeh.githubusers.features.profile.presentation

sealed class ProfileEvent {
    data object OnWebIconClicked : ProfileEvent()
    data object OnBackIconClicked : ProfileEvent()
    data class OnReposiotryClicked(val url: String) : ProfileEvent()
    data object OnFollowersClicked : ProfileEvent()
    data object OnFollowingClicked : ProfileEvent()
}