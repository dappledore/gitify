package com.bluesky.gitify.data.api

import com.bluesky.gitify.data.models.RepositoryDto
import com.bluesky.gitify.data.models.UserDto
import com.bluesky.gitify.data.models.UsersResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): UsersResponseDto

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): UserDto

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") userName: String): List<RepositoryDto>
}