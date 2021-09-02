package com.github.domain.usecases

import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.base.BaseRequest
import com.github.domain.base.BaseUseCase
import com.github.domain.repository.GithubRepository


class GetUserRepositoriesRequest(var username: String?) : BaseRequest {

    override fun validate(): List<String> {
        return if(username.isNullOrBlank())
            listOf("Please enter Username")
        else
            emptyList()
    }
}
class GetUserRepositoriesUseCase(private val githubRepository: GithubRepository): BaseUseCase<GetUserRepositoriesRequest, List<UserRepositoryModel>>() {

    override suspend fun run() = githubRepository.getUserRepositories(request)

}
