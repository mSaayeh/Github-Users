package com.msayeh.githubusers.features.profile.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.wantedUrl, key2 = state.navigateBack) {
        if (state.navigateBack) {
            navController.navigateUp()
        }
        if (state.wantedUrl != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.wantedUrl))
            navController.context.startActivity(intent)
        }
        viewModel.resetEvents()
    }

    ProfileContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(state: ProfileState, onEvent: (ProfileEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.profile?.username ?: "") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(ProfileEvent.OnBackIconClicked) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    state.profile?.let {
                        IconButton(onClick = { onEvent(ProfileEvent.OnWebIconClicked) }) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Open in browser"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.errorMessage != null) {
                Text(text = "${state.errorMessage}")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = state.profile!!.avatarUrl,
                                contentDescription = "Profile image",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(250.dp)
                                    .clip(CircleShape),
                            )
                            Text(
                                text = state.profile.name ?: state.profile.username,
                                style = MaterialTheme.typography.displaySmall,
                            )
                            if (state.profile.name != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = state.profile.username,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Followers: ${state.profile.followers}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.clickable {
                                        onEvent(ProfileEvent.OnFollowersClicked)
                                    }
                                )
                                Spacer(modifier = Modifier.width(32.dp))
                                Text(
                                    text = "Following: ${state.profile.following}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.clickable {
                                        onEvent(ProfileEvent.OnFollowingClicked)
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(24.dp))
                            state.profile.bio?.let {
                                Text(
                                    text = "Bio",
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                Text(
                                    text = it, style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Public Repositories",
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(
                                    text = state.profile.publicReposCount.toString(),
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                    state.profile?.repositories?.let { repos ->
                        items(repos) { repository ->
                            ListItem(
                                headlineContent = { Text(text = repository.name) },
                                overlineContent = { repository.language?.let { Text(text = it) } },
                                trailingContent = {
                                    Box(
                                        modifier = Modifier.size(64.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = "Stars",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Text(
                                            text = repository.starsCount.toString(),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                },
                                modifier = Modifier.clickable {
                                    onEvent(ProfileEvent.OnReposiotryClicked(repository.url))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
