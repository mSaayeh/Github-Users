package com.msayeh.githubusers.features.search.presentation

import com.msayeh.githubusers.features.search.domain.entities.User

data class SearchState(
    val query: String = "",
    val users: List<User> = emptyList(),
    val isSearchVisible: Boolean = false,
    val clickedUsername: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val currentPage: Int = 1,
)