package com.msayeh.githubusers.features.search.data.dto

import com.msayeh.githubusers.features.search.domain.entities.User

data class SearchResponse(
    val items: List<User>,
)