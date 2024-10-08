package com.bluesky.gitify.data.models

/**
 * Users response meta data and list
 */
data class UsersResponseDto (
        val total_count: Int,
        val incomplete_results: Boolean,
        val items: List<UserDto>
)
