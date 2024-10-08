package com.bluesky.gitify.data.mappers

import com.bluesky.gitify.data.models.RepositoryDto
import com.bluesky.gitify.data.models.UserDto
import com.bluesky.gitify.domain.models.Repository
import com.bluesky.gitify.domain.models.User

fun UserDto.toDomainModel(): User {
    return User(id, login, avatarUrl, name, followersCount, followingCount, repoCount, bio, location)
}

fun RepositoryDto.toDomainModel(): Repository {
    return Repository(name, language, stars, description, fork, url)
}