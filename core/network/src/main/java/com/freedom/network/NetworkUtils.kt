package com.freedom.network

import android.util.Log
import com.freedom.common.ApiException
import com.freedom.common.NetworkException
import com.freedom.common.NetworkResult
import com.freedom.common.UnknownException
import com.freedom.network.model.ErrorResponseDto
import io.reactivex.rxjava3.core.Single
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

private val json = Json { ignoreUnknownKeys = true }

fun <T : Any, R> safeApiCall(
    apiCall: () -> Single<T>,
    dataMapper: (T) -> R,
    mapToDomainException: (Throwable) -> Throwable = ::mapToDomainException,
): Single<NetworkResult<R>> {
    return apiCall()
        .map { dto ->
            runCatching { dataMapper(dto) }
                .fold(
                    onSuccess = { NetworkResult.Success(it) },
                    onFailure = { mappingError ->
                        Log.e(TAG, "Data mapping failed", mappingError)
                        handleError(mappingError, mapToDomainException)
                    }
                )
        }
        .onErrorReturn { networkError ->
            Log.e(TAG, "API call failed", networkError)
            handleError(networkError, mapToDomainException)
        }
}

private fun handleError(
    throwable: Throwable,
    mapToDomainException: (Throwable) -> Throwable,
): NetworkResult.Error {
    val domainException = mapToDomainException(throwable)
    return NetworkResult.Error(domainException.message ?: "Unknown error")
}
private fun getErrorBodyString(httpException: HttpException): String? {
    return try {
        httpException.response()?.errorBody()?.string()
    } catch (e: Exception) {
        Log.e(TAG, "Failed to read error body", e)
        null
    }
}

private fun parseError(rawJson: String?): ErrorResponseDto? {
    if (rawJson.isNullOrBlank()) return null

    return runCatching {
        json.decodeFromString(ErrorResponseDto.serializer(), rawJson)
    }.onFailure {
        Log.e(TAG, "Failed to parse error body", it)
    }.getOrNull()
}

private fun mapToDomainException(throwable: Throwable): Throwable = when (throwable) {
    is IOException -> NetworkException(
        message = "Network error: ${throwable.message}",
        cause = throwable,
    )

    is HttpException -> {
        val httpCode = throwable.code()
        val rawBody = getErrorBodyString(throwable)
        val errorResponse = parseError(rawBody)
        ApiException(
            message =  when (errorResponse?.error) {
                NOT_FOUND -> "Not found: ${errorResponse.key ?: "resource"}"
                else -> errorResponse?.error ?: throwable.message()
            },
            code = httpCode,
            cause = throwable,
        )
    }

    else -> UnknownException(
        message = "Unexpected error: ${throwable.message}",
        cause = throwable,
    )
}

private const val NOT_FOUND = "notfound"
private const val TAG = "SafeApiCall"

