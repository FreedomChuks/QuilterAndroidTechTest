package com.freedom.network

import BookDto
import io.reactivex.rxjava3.core.Single

interface NetworkDatasource {
    suspend fun getBooks(): Single<BookDto>
}