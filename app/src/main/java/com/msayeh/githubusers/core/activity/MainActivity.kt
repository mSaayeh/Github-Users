package com.msayeh.githubusers.core.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msayeh.githubusers.core.theme.GithubUsersTheme
import com.msayeh.githubusers.features.profile.presentation.ProfileScreen
import com.msayeh.githubusers.features.search.presentation.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubUsersTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.Search.route,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    composable(Route.Search.route) {
                        SearchScreen(navController)
                    }
                    composable(
                        Route.Profile.route,
                        arguments = listOf(
                            navArgument(Route.Profile.ARG_USERNAME) {
                                type = NavType.StringType
                                nullable = false
                            },
                        ),
                    ) {
                        ProfileScreen(navController)
                    }
                }
            }
        }
    }
}