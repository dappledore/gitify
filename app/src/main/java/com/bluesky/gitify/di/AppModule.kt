package com.bluesky.gitify.di

import com.bluesky.gitify.common.Constants
import com.bluesky.gitify.data.api.AuthInterceptor
import com.bluesky.gitify.data.api.GitHubApiService
import com.bluesky.gitify.data.repository.UserRepositoryImpl
import com.bluesky.gitify.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //DI is used to map api implmentation, can swap out for testing fakes
    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON conversion
            .build()
            .create(GitHubApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideUserRepository(api: GitHubApiService): UserRepository {
        return UserRepositoryImpl(api)
    }

}