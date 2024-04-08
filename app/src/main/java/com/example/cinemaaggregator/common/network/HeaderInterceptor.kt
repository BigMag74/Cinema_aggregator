package com.example.cinemaaggregator.common.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", KinopoiskApiTokenConst.TOKEN)

        return chain.proceed(request.build())
    }
}