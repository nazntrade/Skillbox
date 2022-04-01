package com.skillbox.github.ui.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
//////?????????????????????????????????????????????????????????????
class AuthorisationCustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().signedRequest()
        return chain.proceed(newRequest)
    }

    private fun Request.signedRequest(): Request {
        val accessToken = AccessToken.token
        return newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
    }
}