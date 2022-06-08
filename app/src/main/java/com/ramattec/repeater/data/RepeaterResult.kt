package com.ramattec.repeater.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class RepeaterResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : RepeaterResult<T>()
    data class Error(val exception: String) : RepeaterResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}