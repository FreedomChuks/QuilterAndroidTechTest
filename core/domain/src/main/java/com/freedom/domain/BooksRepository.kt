package com.freedom.domain

import com.freedom.common.NetworkResult
import com.freedom.model.Books
import io.reactivex.rxjava3.core.Single

interface BooksRepository {
    fun getBooks(): Single<NetworkResult<Books>>
}