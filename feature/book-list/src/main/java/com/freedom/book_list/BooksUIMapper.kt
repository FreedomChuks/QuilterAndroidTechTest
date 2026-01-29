package com.freedom.book_list

import com.freedom.model.Books
import com.freedom.model.BooksItem

fun Books.toUiModel() = BooksUi(
    books = books.orEmpty().map { it.toUiModel() }
)

private fun BooksItem.toUiModel() = BookItemUi(
    title = title,
    authorsName = authorNames,
    firstPublishYear = firstPublishYear,
    coverUrl = buildCoverUrl(coverKey)
)

private fun buildCoverUrl(coverKey: String?): String? {
    return coverKey?.let { "https://covers.openlibrary.org/b/OLID/$it-L.jpg" }
}