package com.happymesport.merchant.common

sealed class Resources<T>(
    val data: T?,
    val exception: Exception? = null,
    val message: String? = null,
) {
    class Success<T>(
        data: T?,
    ) : Resources<T>(data)

    class Error<T>(
        message: String?,
        data: T? = null,
        exception: Exception? = null,
    ) : Resources<T>(data, exception, message)

    class Loading<T>(
        data: T? = null,
    ) : Resources<T>(data)
}
