package com.freedom.model

data class Books(
    val books: List<BooksItem>?
)

data class BooksItem(
    val title: String,
    val authorNames: String,
    val firstPublishYear: Int,
    val coverKey: String?
)