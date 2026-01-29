package com.freedom.book_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.freedom.book_list.component.DetailsBottomSheetContent
import com.freedom.book_list.component.BookItem
import com.freedom.book_list.component.EmptyState
import com.freedom.book_list.component.ErrorState
import com.freedom.book_list.component.LoadingState
import com.freedom.book_list.model.BookItemUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen() {
    val viewModel: BooksViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedBook by remember { mutableStateOf<BookItemUi?>(null) }
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.loadBooks()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { Text("Books") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is BooksUiState.Loading -> LoadingState()
                is BooksUiState.Success -> {
                    val successState = uiState as BooksUiState.Success
                    BookListContent(
                        books = successState.ui.books,
                        onBookClick = { book -> selectedBook = book }
                    )
                }
                is BooksUiState.Error -> {
                    val errorState = uiState as BooksUiState.Error
                    ErrorState(
                        message = errorState.message,
                        onRetry = { viewModel.loadBooks() }
                    )
                }
            }
        }
    }

    if (selectedBook != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedBook = null },
            sheetState = bottomSheetState
        ) {
            DetailsBottomSheetContent(book = selectedBook!!)
        }
    }
}

@Composable
fun BookListContent(
    books: List<BookItemUi>,
    onBookClick: (BookItemUi) -> Unit,
    modifier: Modifier = Modifier
) {
    if (books.isEmpty()) {
        EmptyState()
    } else {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                BookItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    bookItemUi = book,
                    onBookClick = onBookClick
                )
            }
        }
    }
}


