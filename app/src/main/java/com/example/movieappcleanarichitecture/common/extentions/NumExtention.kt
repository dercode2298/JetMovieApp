package com.example.movieappcleanarichitecture.common.extentions

fun Double?.convertToPercentageString(): String {
    return "${((this ?: 0).toLong() * 10)} %"
}