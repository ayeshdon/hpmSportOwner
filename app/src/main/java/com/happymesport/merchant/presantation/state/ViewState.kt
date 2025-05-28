package com.happymesport.merchant.presantation.state

data class ViewState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = "",
)
