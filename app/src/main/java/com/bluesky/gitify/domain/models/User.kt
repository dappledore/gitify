package com.bluesky.gitify.domain.models

// Domain model representing a user in your app.
// This model is independent of the API and used in ViewModel/UI.
data class User(
    val id: Int, // Unique ID
    val username: String, // GitHub username
    val avatarUrl: String, // URL to user's avatar
    val fullName: String?, // User's full name (can be null)
    val followersCount: Int, // Number of followers
    val followingCount: Int,
    val repoCount: Int,
    val bio: String?,
    val location: String?
)
