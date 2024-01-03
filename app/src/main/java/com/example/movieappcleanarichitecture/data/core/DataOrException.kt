package com.example.movieappcleanarichitecture.data.core

data class DataOrException<T>(
    var data:T? = null,
    var loading: Boolean? = null,
    var error: Exception? = null
)