package com.bluesky.gitify.presentation.ui.screens.user_detail

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bluesky.gitify.domain.models.Repository

@Composable
fun UserDetailScreen(
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {

    val userState = viewModel.user.value
    val repoState = viewModel.repositories.value

    Box(modifier = Modifier.fillMaxSize()) {


        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp),) {

            // Back button to return to the previous screen
            IconButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier.size(24.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colors.primary)
            }

            UserProfile(userState)

            Text("Unforked Repos:",style = MaterialTheme.typography.h2, color = MaterialTheme.colors.onSurface)

            LazyColumn {
                items(repoState.repos) { repo ->
                    RepositoryRow(repo) { url ->
                        navController.navigate("webview/${Uri.encode(url)}")
                    }
                }
            }
        }



        // Display error message if there's any
        if (userState.error.isNotBlank()) {
            Text(
                text = userState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }

        // Display loading indicator if loading
        if (userState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun UserProfile(userState: UserState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(userState.user?.avatarUrl),
            contentDescription = null,
            contentScale = ContentScale.None,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(2.dp))
                .padding(end = 10.dp)
        )

        // Column to stack the user details text next to the image
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            UserText("User: ${userState.user?.username}")
            UserText("Fullname: ${userState.user?.fullName ?: "Unknown"}")
            UserText("Followers: ${userState.user?.followersCount}")
            UserText("Following: ${userState.user?.followingCount}")
            UserText("Repo Count: ${userState.user?.repoCount}")
        }
    }
    userState.user?.location?.let {  UserText("\uD83D\uDCCDLocation: ${it}") }

    userState.user?.bio?.let {  UserText("Bio: ${it}") }
}

@Composable
fun UserText(text: String) {
    Text(text = text, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.primary)
}

@Composable
fun RepoText(text: String) {
    Text(text = text, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.secondary)
}

@Composable
fun RepositoryRow(repository: Repository, onRepoClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRepoClick(repository.url) } // Open the repo's URL in WebView
            .padding(16.dp)
    ) {
        RepoText(text = "Name: ${repository.name}")
        RepoText(text = "${repository.language ?: ""} ${repository.stars}★")
        repository.description?.let { RepoText(text = it) }
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 4.dp))
    }
}
