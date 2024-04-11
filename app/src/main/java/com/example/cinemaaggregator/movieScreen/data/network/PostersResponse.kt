package com.example.cinemaaggregator.movieScreen.data.network

import com.example.cinemaaggregator.common.network.Response
import com.example.cinemaaggregator.searchScreen.domain.model.Poster

class PostersResponse(
    val docs: List<Poster>
) : Response()
