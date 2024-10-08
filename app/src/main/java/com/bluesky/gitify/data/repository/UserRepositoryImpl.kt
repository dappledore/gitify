package com.bluesky.gitify.data.repository

import com.bluesky.gitify.data.api.GitHubApiService
import com.bluesky.gitify.data.models.RepositoryDto
import com.bluesky.gitify.data.models.UserDto
import com.bluesky.gitify.data.models.UsersResponseDto
import com.bluesky.gitify.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Concrete User repo implementation
 */
class UserRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService
) : UserRepository {

    // Implement the method to fetch users based on the search query
    override suspend fun getUsers(query: String): UsersResponseDto {
        // Make API call to search users and map the response
        return apiService.searchUsers(query)
    }

    override suspend fun getUser(username: String): UserDto {
        val userDto: UserDto = apiService.getUser(username) // Make the API call
        return userDto // Convert to domain model
    }

    // Fetch repositories for a specific user, excluding forked ones
    override suspend fun getUserRepositories(username: String): List<RepositoryDto> {
        return apiService.getUserRepositories(username) // Fetch repositories
            .filter { !it.fork } // Exclude forked repositories
    }
}