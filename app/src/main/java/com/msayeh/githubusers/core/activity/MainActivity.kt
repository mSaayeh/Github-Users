package com.msayeh.githubusers.core.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
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
                SharedTransitionLayout(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Route.Search.route) {
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
                        ) { backStackEntry ->
                            val username =
                                backStackEntry.arguments?.getString(Route.Profile.ARG_USERNAME)!!
                            ProfileScreen(navController, username)
                        }
                    }
                }
            }
        }
    }
}