package com.msayeh.githubusers.features.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msayeh.githubusers.core.util.Resource
import com.msayeh.githubusers.features.search.domain.usecase.SearchUsersUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUsersUsecase: SearchUsersUsecase): ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.Search -> searchUsers()
            is SearchEvent.LoadMore -> loadMoreUsers()
            is SearchEvent.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            is SearchEvent.OnUserClicked -> onUserClicked(event.username)
            SearchEvent.ToggleSearchVisibility -> toggleSearchVisibility()
        }
    }

    fun resetEvents() {
        _state.update {
            it.copy(
                clickedUsername = null
            )
        }
    }

    private fun toggleSearchVisibility() {
        _state.update {
            it.copy(
                isSearchVisible = !it.isSearchVisible
            )
        }
    }

    private fun onUserClicked(username: String) {
        _state.update {
            it.copy(
                clickedUsername = username
            )
        }
    }

    private fun loadMoreUsers() {
        TODO("Not yet implemented")
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update {
            it.copy(
                query = query
            )
        }
    }

    private fun searchUsers() {
        Log.e("SearchViewModel", "searchUsers: ${state.value.query}")
        viewModelScope.launch {
            searchUsersUsecase(state.value.query, state.value.currentPage).collect { resource ->
                when(resource) {
                    is Resource.Loading -> {
                        Log.e("SearchViewModel", "Loading")
                        _state.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            Log.e("SearchViewModel", "Error")
                            it.copy(
                                isLoading = false,
                                errorMessage = resource.exception!!.localizedMessage
                            )
                        }
                    }
                    is Resource.Success -> {
                        Log.e("SearchViewModel", "Success")
                        if (resource.data!!.isEmpty() && state.value.users.isEmpty()) {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "No users found."
                                )
                            }
                        } else {
                            _state.update {
                                it.copy(
                                    users = resource.data,
                                    isLoading = false,
                                    errorMessage = null
                                )
                            }
                            Log.e("SearchViewModel", "searchUsers: ${state.value.users}")
                        }
                    }
                }
            }
        }
    }
}