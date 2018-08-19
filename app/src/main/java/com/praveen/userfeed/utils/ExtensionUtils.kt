package com.praveen.userfeed.utils

fun String.pluralize(count: Int): String? {
    return if (count > 1) {
        this + 's'
    } else {
        this
    }
}