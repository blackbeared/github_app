package com.github.presentation.search_repository.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.github.app.extensions.launchSilent
import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.usecases.GetUserRepositoriesRequest
import com.github.domain.usecases.GetUserRepositoriesUseCase
import com.github.domain.utils.Result
import com.github.presentation.base.BaseAdapter
import com.github.presentation.base.BaseModel
import com.github.presentation.search_repository.UserRepositoryObservable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ListUserRepositoryViewModel(private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase, private val coroutineContext: CoroutineContext) : ViewModel() {

    private var job: Job = Job()
    lateinit var adapter: BaseAdapter<BaseModel>

    val username : ObservableField<String> = ObservableField("")
    val loading : ObservableBoolean = ObservableBoolean(false)
    val baseModels = mutableListOf<BaseModel>()
    val noData : ObservableBoolean
        get() = ObservableBoolean(baseModels.isNullOrEmpty())

    fun fetchUserRepositories(view: View) = launchSilent(coroutineContext, job) {
        loading.set(true)
        baseModels.clear()
        val request = GetUserRepositoriesRequest(username.get())
        val response = getUserRepositoriesUseCase.execute(request)
        loading.set(false)
        launch(context = Dispatchers.Main){
            processUserRepositories(response, view)
        }
    }

    private fun processUserRepositories(response: Result<List<UserRepositoryModel>>, view: View){
        if (response is Result.Success) {
            response.data.forEach {
                baseModels.add(UserRepositoryObservable(it))
            }
            adapter.notifyDataSetChanged()
        } else if (response is Result.Failure) {
            Log.d("error", response.exception.message.toString())
            adapter.notifyDataSetChanged()
        }
    }
}