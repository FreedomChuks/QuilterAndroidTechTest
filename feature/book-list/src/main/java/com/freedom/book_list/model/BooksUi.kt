package com.freedom.book_list.model

data class BooksUi(
    val books: List<BookItemUi>
)

data class BookItemUi(
    val title: String?,
    val authorsName: String?,
    val firstPublishYear: Int?,
    val coverUrl: String?
)