package com.freedom.data.mapper

import BookDto
import ReadingLogEntriesItem
import com.freedom.model.BooksItem
import com.freedom.model.Books as BooksDomainModel

fun BookDto.toDomainModel() = BooksDomainModel(
    books = data.mapNotNull { it.toBookItem() }
)

private fun ReadingLogEntriesItem.toBookItem(): BooksItem? {
    val work = this.work ?: return null

    return BooksItem(
        title = work.title.orEmpty(),
        authorNames = work.authorNames.joinToString(", "),
        firstPublishYear = work.firstPublishYear ?: 0,
        coverKey = work.coverEditionKey,
    )
}