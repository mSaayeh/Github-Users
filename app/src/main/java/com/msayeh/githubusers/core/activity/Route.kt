package com.msayeh.githubusers.core.activity

sealed class Route(val route: String) {
    data object Search : Route("search")

    data object Profile : Route("profile/{username}") {
        const val ARG_USERNAME = "username"
        fun createRoute(username: String): String = "profile/$username"
    }
}