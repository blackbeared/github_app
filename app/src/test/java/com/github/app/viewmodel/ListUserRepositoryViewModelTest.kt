package com.github.app.viewmodel


import android.text.format.DateUtils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.app.di.Modules
import com.github.data.service.entity.UserRepositoryModel
import com.github.domain.repository.GithubRepository
import com.github.domain.usecases.GetUserRepositoriesRequest
import com.github.domain.usecases.GetUserRepositoriesUseCase
import com.github.domain.utils.ERROR_LOADING_DATA
import com.github.domain.utils.Result
import com.github.domain.utils.Result.Loading
import com.github.domain.utils.Status
import com.github.presentation.search_repository.viewmodel.ListUserRepositoryViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import java.lang.Thread.sleep
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import org.awaitility.Awaitility.await
import org.awaitility.kotlin.untilNotNull
import org.awaitility.kotlin.untilNull
import java.util.*
import kotlin.test.assertEquals

/// @author sandipsavaliya
/// Created 03/09/21 at 11:17 AM
/// [ListUserRepositoryViewModelTest] class


private const val VALID_USERNAME = "blackbeared"
private const val INVALID_USERNAME = "@@@"

class ListUserRepositoryViewModelTest : AutoCloseKoinTest() {

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(Modules.dataModel,Modules.domainModel,Modules.useCaseModel, Modules.viewModel)
    }


    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        declareMock<GetUserRepositoriesUseCase>()
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        stopKoin()
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun onSearchRemoteTestSuccessful() = runBlocking {
        val githubService: GithubRepository by inject()
        val response = githubService.getUserRepositories(GetUserRepositoriesRequest(VALID_USERNAME))

        assertEquals(response.status, Status.SUCCESS)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun onSearchRemoteTestError() = runBlocking {
        val githubService: GithubRepository by inject()
        val response = githubService.getUserRepositories(GetUserRepositoriesRequest(INVALID_USERNAME))

        assertEquals(response.status, Status.ERROR)
    }
}
