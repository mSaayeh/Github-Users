package com.msayeh.githubusers.features.search.presentation

sealed class SearchEvent {
    data class OnSearchQueryChanged(val query: String): SearchEvent()
    data class OnUserClicked(val username: String): SearchEvent()
    data object ToggleSearchVisibility: SearchEvent()
    data object Search: SearchEvent()
    data object LoadMore: SearchEvent()
}