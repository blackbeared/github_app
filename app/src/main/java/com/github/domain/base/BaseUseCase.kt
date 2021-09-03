package com.github.domain.base

import kotlinx.coroutines.channels.Channel
import com.github.domain.utils.Result
import java.lang.Exception

interface BaseRequest {
    fun validate(): List<String>
}

abstract class BaseUseCase<R : BaseRequest , T>() {

    lateinit var request: R
    private var channel= Channel<Result<T>>(Channel.UNLIMITED)

    open suspend fun execute(request: R): Result<T> {
        this.request = request

        val validated = request.validate()
        if (validated.isEmpty()) return run()
        return Result.Failure(exception = Exception(validated.first()))
    }

    abstract suspend fun run(): Result<T>

    fun getChannel() = channel
}
