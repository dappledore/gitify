package com.bluesky.gitify.domain.repository


import com.bluesky.gitify.data.models.RepositoryDto
import com.bluesky.gitify.data.models.UserDto
import com.bluesky.gitify.data.models.UsersResponseDto

interface UserRepository {
    suspend fun getUsers(query: String): UsersResponseDto
    suspend fun getUser(username: String): UserDto
    suspend fun getUserRepositories(username: String): List<RepositoryDto>
}
