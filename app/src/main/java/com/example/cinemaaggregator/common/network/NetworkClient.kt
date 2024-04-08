package com.example.cinemaaggregator.common.network

interface NetworkClient {

    suspend fun doRequest(dto: Any): Response
}