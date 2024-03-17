package com.example.movietrailer.data.tmdbSource

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbRetrofit {
    private const val AUTHORIZATION_HEADER_KEY = "api_key"
    private const val AUTHORIZATION_HEADER_VALUE = "1dbf7e773954c1b18244d0d866ca6a44"

    val retrofit: Retrofit? by lazy { makeRetrofit() }

    private fun makeRetrofit(): Retrofit? {
        val okHttpClient = makeOkhttpClient()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun makeOkhttpClient(): OkHttpClient {
        val headerInterceptor = makeAddApiKeyQueryParamInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .build()
    }

    private fun makeAddApiKeyQueryParamInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originRequest = chain.request()
            val originUrl = originRequest.url()

            val newUrl = originUrl.newBuilder()
                .addQueryParameter(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_VALUE)
                .build()
            val newRequest = originRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }
}