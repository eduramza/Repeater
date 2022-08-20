package com.ramattec.domain

sealed class NetworkResult<T> {
    data class Progress<T>(val data: T? = null) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val e: Throwable) : NetworkResult<T>()
}