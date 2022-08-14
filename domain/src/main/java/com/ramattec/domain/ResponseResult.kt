package com.ramattec.domain

sealed class ResponseResult<T> {
    data class Progress<T>(val data: T? = null) : ResponseResult<T>()
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Failure<T>(val e: Throwable) : ResponseResult<T>()
}