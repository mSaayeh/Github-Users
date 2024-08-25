@file:OptIn(ExperimentalMaterial3Api::class)

package com.msayeh.githubusers.features.search.presentation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.msayeh.githubusers.core.activity.Route
import com.msayeh.githubusers.features.search.presentation.components.SearchField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.clickedUsername) {
        state.clickedUsername?.let { username ->
            Log.e("SearchScreen", "Navigating to profile screen: ${Route.Profile.createRoute(username)}")
            navController.navigate(Route.Profile.createRoute(username))
            viewModel.resetEvents()
        }
    }

    SearchContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun SearchContent(state: SearchState, onEvent: (SearchEvent) -> Unit) {
    Scaffold(
        topBar = {
            AnimatedContent(
                targetState = state.isSearchVisible, label = "SearchBarAnimation",
                modifier = Modifier.background(Color.Transparent),
            ) {
                if (it)
                    SearchField(
                        query = state.query,
                        onQueryChange = { query ->
                            onEvent(SearchEvent.OnSearchQueryChanged(query))
                        },
                        onCancelClicked = { onEvent(SearchEvent.ToggleSearchVisibility) },
                        onSearchClicked = { onEvent(SearchEvent.Search) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                            .systemBarsPadding(),
                    )
                else
                    TopAppBar(
                        title = {
                            Text(
                                text = "Github Users",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        },
                        actions = {
                            IconButton(onClick = { onEvent(SearchEvent.ToggleSearchVisibility) }) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = "Search",
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val lazyListState = rememberLazyListState()
            LaunchedEffect(key1 = lazyListState.firstVisibleItemIndex) {
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.let { lastItem ->
                    Log.e("SearchScreen", "lastItem.index: ${lastItem.index}")
                    if (lastItem.index >= (state.currentPage * 20) - 3) {
                            Log.d("SearchScreen", "Ask for more users")
                            onEvent(SearchEvent.LoadMore)
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.errorMessage != null) {
                Text(text = state.errorMessage)
            } else if (state.users.isEmpty() && !state.isSearchVisible) {
                Text(text = "Press the search icon to start searching for users")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
                    items(state.users, key = { it.id }) { user ->
                        ListItem(
                            headlineContent = { Text(text = user.username) },
                            leadingContent = {
                                AsyncImage(
                                    model = user.avatarUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape),
                                )
                            },
                            modifier = Modifier.clickable {
                                onEvent(SearchEvent.OnUserClicked(user.username))
                            }
                        )
                    }
                }
            }
        }
    }
}
