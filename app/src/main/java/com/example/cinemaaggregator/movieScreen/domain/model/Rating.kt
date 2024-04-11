package com.example.cinemaaggregator.movieScreen.domain.model

data class Rating(val kp: Float) {

    override fun toString(): String {
        return kp.toString()
    }
}