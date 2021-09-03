package com.github.presentation.search_repository.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.github.app.extensions.launchSilent
import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.usecases.GetUserRepositoriesRequest
import com.github.domain.usecases.GetUserRepositoriesUseCase
import com.github.domain.utils.Result
import com.github.domain.utils.Status
import com.github.presentation.base.BaseAdapter
import com.github.presentation.base.BaseModel
import com.github.presentation.base.BaseViewModel
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.Future


class ListUserRepositoryViewModel(private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase) : BaseViewModel() {

    lateinit var adapter: BaseAdapter<BaseModel>

    val username : ObservableField<String> = ObservableField("")
    val baseModels = mutableListOf<BaseModel>()

    val repositories = MutableLiveData<Result<List<UserRepositoryModel>>>(Result.Failure(exception = Exception("Start searching by entering username")))

    fun fetchUserRepositories() = launch {
        repositories.postValue(Result.Loading())
        baseModels.clear()
        val request = GetUserRepositoriesRequest(username.get())
        val response = getUserRepositoriesUseCase.execute(request)
        repositories.postValue(response)
    }
}