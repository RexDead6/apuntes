package com.developersandgraphics.apuntedomino.back.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://appdomino.criptoluck.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}