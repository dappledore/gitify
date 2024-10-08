package com.bluesky.gitify.presentation.ui.screens.user_list

import com.bluesky.gitify.domain.models.User

data class UserListState(
        val isLoading: Boolean = false,
        val users: List<User> = emptyList(),
        val error: String = ""
)
