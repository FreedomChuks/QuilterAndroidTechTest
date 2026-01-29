package com.freedom.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val error: String? = null,
    val key: String? = null
)