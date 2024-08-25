package com.msayeh.githubusers.features.profile.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msayeh.githubusers.core.activity.Route
import com.msayeh.githubusers.core.util.Resource
import com.msayeh.githubusers.features.profile.domain.usecase.GetProfileUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProfileUsecase: GetProfileUsecase
) :
    ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        val username: String = checkNotNull(savedStateHandle[Route.Profile.ARG_USERNAME])
        getProfile(username)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnWebIconClicked -> {
                _state.update {
                    it.copy(wantedUrl = state.value.profile?.url)
                }
            }

            ProfileEvent.OnBackIconClicked -> {
                _state.update {
                    it.copy(navigateBack = true)
                }
            }

            is ProfileEvent.OnReposiotryClicked -> {
                _state.update {
                    it.copy(wantedUrl = event.url)
                }
            }

            ProfileEvent.OnFollowersClicked -> {
                _state.update {
                    it.copy(wantedUrl = state.value.profile?.url + "?tab=followers")
                }
            }

            ProfileEvent.OnFollowingClicked -> {
                _state.update {
                    it.copy(wantedUrl = state.value.profile?.url + "?tab=following")
                }
            }
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(wantedUrl = null, navigateBack = false)
        }
    }

    private fun getProfile(username: String) {
        viewModelScope.launch {
            getProfileUsecase(username).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.exception!!.message,
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true, errorMessage = null) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                profile = result.data,
                                isLoading = false,
                                errorMessage = null,
                            )
                        }
                    }
                }
            }
        }
    }
}