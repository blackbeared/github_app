package com.github.data.repository

import android.util.Log
import com.github.data.service.api.GithubService
import com.github.domain.utils.Result
import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.repository.GithubRepository
import com.github.domain.usecases.GetUserRepositoriesRequest

class GithubRepositoryImpl(private val githubService: GithubService) : GithubRepository {

    override suspend fun getUserRepositories(
        request: GetUserRepositoriesRequest,
    ): Result<List<UserRepositoryModel>> {
        return try {
            val response = githubService.getUserRepositoriesAsync(request.username!!)
            if (response.isSuccessful) {
                Result.Success(response.body() as List<UserRepositoryModel>)
            } else {
                Result.Failure(Exception(response.message()))
            }
        } catch (ex: Exception) {
            Log.d("ERROR", ex.message.toString())
            Result.Failure(ex)
        }
    }
}