package com.github.data.repository

import com.github.data.service.api.GithubService
import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.repository.GithubRepository
import com.github.domain.usecases.GetUserRepositoriesRequest
import com.github.domain.utils.Result

class GithubRepositoryImpl(private val githubService: GithubService) : GithubRepository {

    override suspend fun getUserRepositories(
        request: GetUserRepositoriesRequest,
    ): Result<List<UserRepositoryModel>> {
        return try {
            val response = githubService.getUserRepositoriesAsync(request.username!!)
            if(response.body() == null){
                throw java.lang.Exception("No user found with given username")
            }else{
                if (response.isSuccessful) {
                    Result.Success(data = response.body() as List<UserRepositoryModel>, message = "Repositories Loaded Successfully")
                } else {
                    Result.Failure(exception = Exception(response.message()))
                }
            }
        } catch (exception: Exception) {
            Result.Failure(exception = exception)
        }
    }
}