package com.skillbox.lessons_networking.network

import okhttp3.Interceptor
import okhttp3.Response

//  1.7 ????       implementation "com.squareup.okhttp3:logging-interceptor:4.8.0"
// L25.4 t8.44
class CustomHeaderInterceptor(
    private val headerName: String,
    private val headerValue: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .addHeader(headerName, headerValue)
            .build()

        return chain.proceed(modifiedRequest)
    }
}