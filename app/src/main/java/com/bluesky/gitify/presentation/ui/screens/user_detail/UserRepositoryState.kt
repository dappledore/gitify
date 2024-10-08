package com.bluesky.gitify.presentation.ui.screens.user_detail

import com.bluesky.gitify.domain.models.Repository

data class UserRepositoryState(
    val isLoading: Boolean = false,
    val repos: List<Repository> = emptyList(),
    val error: String = ""
)