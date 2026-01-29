package com.freedom.network

import BookDto
import com.freedom.network.retrofit.BookApiServices
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val bookApiServices: BookApiServices
): NetworkDatasource {
    override fun getBooks(): Single<BookDto> = bookApiServices.getBooks()
}