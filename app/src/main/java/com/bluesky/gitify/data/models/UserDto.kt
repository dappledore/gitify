package com.bluesky.gitify.data.models

import com.google.gson.annotations.SerializedName

// Data class representing the structure of the user data returned by the GitHub API
data class UserDto(
    val id: Int, // User ID from the API
    @SerializedName("login") val login: String, // Username from the API (mapped to 'login')
    @SerializedName("avatar_url") val avatarUrl: String, // User's avatar URL from the API
    @SerializedName("name") val name: String?, // Full name (optional, can be null)
    @SerializedName("followers") val followersCount: Int, // Number of followers from the API
    @SerializedName("following") val followingCount: Int,
    @SerializedName("public_repos") val repoCount: Int,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?
)
