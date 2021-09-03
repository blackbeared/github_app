package com.github.app.di

import com.github.data.service.api.GithubService
import com.github.data.service.constant.ApiConstants
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

object NetworkModule {

    fun provideNetworkService(retrofit: Retrofit): GithubService = retrofit.create(GithubService::class.java)

    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

}