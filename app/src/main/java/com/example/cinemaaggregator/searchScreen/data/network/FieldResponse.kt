package com.example.cinemaaggregator.searchScreen.data.network

import com.example.cinemaaggregator.common.network.Response
import com.example.cinemaaggregator.searchScreen.domain.model.Field

class FieldResponse(
    val names: List<Field>
) : Response()