package com.example.movieappcleanarichitecture.common.extentions

fun String.intelliTrim(): String {
    return if (length > 15) {
        "${substring(0, 15)}..."
    } else {
        this
    }
}
