package com.github.domain.repository

import com.github.domain.utils.Result
import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.usecases.GetUserRepositoriesRequest
import kotlinx.coroutines.channels.Channel

interface GithubRepository {
    suspend fun getUserRepositories(
        request: GetUserRepositoriesRequest,
    ): Result<List<UserRepositoryModel>>
}