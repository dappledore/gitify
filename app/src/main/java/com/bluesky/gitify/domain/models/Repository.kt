package com.bluesky.gitify.domain.models

data class Repository(
    val name: String,
    val language: String?,
    val stars: Int,
    val description: String?,
    val fork: Boolean,
    val url: String
)