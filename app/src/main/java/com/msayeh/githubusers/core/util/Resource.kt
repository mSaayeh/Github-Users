package com.msayeh.githubusers.core.util

sealed class Resource<out T>(val data: T? = null, val exception: Exception? = null) {
    class Success<out T>(data: T?) : Resource<T>(data)
    class Loading<out T>(data: T? = null) : Resource<T>(data)
    class Error<out T>(exception: Exception, data: T? = null) : Resource<T>(data, exception)
}