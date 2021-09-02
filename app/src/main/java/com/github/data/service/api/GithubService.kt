package com.github.data.service.api

import com.github.data.service.constant.ApiConstants
import com.github.data.service.entity.UserRepositoryModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubService {

    @GET(ApiConstants.REPOS)
    suspend fun getUserRepositoriesAsync(@Path("username") username: String): Response<List<UserRepositoryModel>>
}
