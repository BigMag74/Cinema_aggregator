package com.example.cinemaaggregator.movieScreen.data.network

import com.example.cinemaaggregator.common.network.Response
import com.example.cinemaaggregator.movieScreen.domain.model.Season

class SeasonsAndEpisodesResponse(
    val docs: List<Season>,
) : Response()