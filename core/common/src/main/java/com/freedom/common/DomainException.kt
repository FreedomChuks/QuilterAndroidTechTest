package com.freedom.common

sealed class DomainException(
    val code: Int,
    override val message: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(message, cause)

/** Connectivity-related problems (timeouts, no network, etc.) */
class NetworkException(
    message: String,
    cause: Throwable? = null,
) : DomainException(-1, message, cause)

/** Non-successful HTTP response that we could still parse. */
class ApiException(
    message: String,
    cause: Throwable? = null,
    code: Int = -1,
) : DomainException(code, message, cause)

/** We received an error that we couldn’t map to any known type. */
class UnknownException(
    message: String,
    cause: Throwable? = null,
) : DomainException(-1, message, cause)