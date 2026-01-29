package com.freedom.book_list

import com.freedom.book_list.model.BooksUi

sealed class BooksUiState {
    object Loading : BooksUiState()
    data class Success(val ui: BooksUi) : BooksUiState()
    data class Error(val message: String) : BooksUiState()
}