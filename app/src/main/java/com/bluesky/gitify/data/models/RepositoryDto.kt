package com.bluesky.gitify.data.models

import com.google.gson.annotations.SerializedName

// Data class for repository information returned by the GitHub API
data class RepositoryDto(
    @SerializedName("name") val name: String,              // Repository name
    @SerializedName("language") val language: String?,     // Development language (nullable)
    @SerializedName("stargazers_count") val stars: Int,    // Number of stars
    @SerializedName("description") val description: String?, // Repository description (nullable)
    @SerializedName("fork") val fork: Boolean,
    @SerializedName("html_url") val url: String
)
