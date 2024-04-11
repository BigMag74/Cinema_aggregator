package com.example.cinemaaggregator.common.util

fun List<Any>.listToString(): String {
    var res = ""
    forEach {
        res += "$it, "
    }
    return res.dropLast(2)
}