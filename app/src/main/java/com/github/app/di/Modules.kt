package com.github.app.di

import com.github.app.di.NetworkModule.provideGson
import com.github.app.di.NetworkModule.provideNetworkService
import com.github.app.di.NetworkModule.provideRetrofit
import com.github.data.repository.GithubRepositoryImpl
import com.github.domain.repository.GithubRepository
import com.github.domain.usecases.GetUserRepositoriesUseCase
import com.github.presentation.search_repository.viewmodel.ListUserRepositoryViewModel
import org.koin.dsl.module


object Modules {

    val dataModel = module {
        factory { provideGson() }
        single { provideRetrofit(get()) }
        factory { provideNetworkService(get()) }
    }

    val domainModel = module {
        single<GithubRepository> { GithubRepositoryImpl(get()) }
    }

    val useCaseModel = module {
        single { GetUserRepositoriesUseCase(get()) }
    }

    val viewModel = module {
        single{ ListUserRepositoryViewModel(get()) }
    }
}
