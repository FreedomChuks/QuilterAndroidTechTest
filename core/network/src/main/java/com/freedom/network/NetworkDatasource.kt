package com.freedom.network

import BookDto
import io.reactivex.rxjava3.core.Single

interface NetworkDatasource {
    fun getBooks(): Single<BookDto>
}