package com.example.hw_networking.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor(
    private val name: String,
    private val value: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val modifiedHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(name, value)
            .build()

        val modifiedRequestBuilder = originalRequest.newBuilder()
            .url(modifiedHttpUrl)

        val modifiedRequest = modifiedRequestBuilder.build()
        return chain.proceed(modifiedRequest)
    }
}