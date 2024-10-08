package com.bluesky.gitify.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    val accessToken = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "token $accessToken") // Add the Authorization header
            .build()
        return chain.proceed(newRequest)
    }

}