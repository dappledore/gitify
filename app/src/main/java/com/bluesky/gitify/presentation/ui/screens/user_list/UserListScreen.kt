package com.bluesky.gitify.presentation.ui.screens.user_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.bluesky.gitify.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val state by viewModel.users // Collect the state from the ViewModel
    var searchText by remember { mutableStateOf("android") } // Local state for search input
    val searchQueryFlow = remember { MutableStateFlow("") } // Flow for search text
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        searchQueryFlow
            .debounce(500L) // Restrict to finish typing
            .collectLatest { query ->
                if (query.isNotEmpty()) {
                    viewModel.searchUsers(query) // Only call searchUsers after debouncing
                }
            }
    }

    // Get the keyboard controller to hide it when needed
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            // Search TextField
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    coroutineScope.launch {
                        searchQueryFlow.emit(searchText)
                    }
                },
                placeholder = { Text("Search users...") },
                singleLine = true, // Set to single line
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Perform search and hide the keyboard
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // User List
            LazyColumn {
                items(state.users) { user ->
                    AnimatedVisibility(
                        visible = true,enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        UserRow(user = user, onClick = {
                            navController.navigate("userDetail/${user.username}") // Navigate to user repository screen
                        })
                    }
                }
            }
        }

        // Display error message if there's any
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }

        // Display loading indicator if loading
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun UserRow(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp) // Add padding around each row for better spacing
    ) {
        Image(
            painter = rememberAsyncImagePainter(user.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp) // Set a fixed size for the image
                .clip(CircleShape) // Make the image circular
        )
        Spacer(modifier = Modifier.width(8.dp)) // Space between image and text
        Column {
            Text(text = user.username, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.primary)
        }
    }
}
