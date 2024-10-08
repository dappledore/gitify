package com.bluesky.gitify.presentation.ui.screens.user_detail

import com.bluesky.gitify.domain.models.User

data class UserState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)