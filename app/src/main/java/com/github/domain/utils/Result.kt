package com.github.domain.utils

sealed class Result<out T> {
    class Success<out T >(val data: T) : Result<T>()
    class Failure(val exception: Exception) : Result<Nothing>()
}

