package com.example.cinemaaggregator.common.util

import com.example.cinemaaggregator.movieScreen.domain.model.Actor
import com.example.cinemaaggregator.movieScreen.domain.model.Person

fun List<Any>.listToString(): String {
    var res = ""
    forEach {
        res += "$it, "
    }
    return res.dropLast(2)
}

fun List<Person>.getDirectors(): String {
    var res = ""
    forEach {
        if (it.enProfession == "director")
            res += "${it.name}\n"
    }
    return res.dropLast(1)
}

fun List<Person>.getOperators(): String {
    var res = ""
    forEach {
        if (it.enProfession == "operator")
            res += "${it.name}\n"
    }
    return res.dropLast(1)
}

fun List<Person>.getActors(): List<Actor> {
    val res = mutableListOf<Actor>()
    forEach {
        if (it.enProfession == "actor")
            res.add(Actor(it.name, it.photo, it.description))
    }
    return res
}