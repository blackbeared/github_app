package com.github.domain.utils

const val LOADING_DATA = "Loading Data"
const val DATA_LOADED = "Data Loaded"
const val ERROR_LOADING_DATA = "Error Loading Data"

enum class Status{
    ERROR,
    SUCCESS,
    LOADING
}

open class Result<out T>(val status: Status, val message: String) {

    override fun hashCode(): Int {
        return status.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return status == (other as Result<*>).status && message == other.message
    }

    open class Loading<out T>(status: Status = Status.LOADING, message: String = LOADING_DATA) : Result<T>(status, message)

    open class Success<out T >(status: Status = Status.SUCCESS, val data: T?, message: String = DATA_LOADED) : Result<T>(status, message)

    open class Failure(status: Status = Status.ERROR, val exception: Exception) : Result<Nothing>(status, exception.localizedMessage?: ERROR_LOADING_DATA)
}

