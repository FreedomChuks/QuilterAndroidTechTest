package com.freedom.network.retrofit

import BookDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BookApiServices{
    @GET("people/mekBot/books/want-to-read.json")
    fun getBooks(): Single<BookDto>
}