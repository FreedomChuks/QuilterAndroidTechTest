package com.freedom.book_list

import androidx.lifecycle.ViewModel
import com.freedom.book_list.mapper.toUiModel
import com.freedom.common.NetworkResult
import com.freedom.domain.BooksRepository
import com.freedom.model.Books
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
     val booksRepository: BooksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val uiState: StateFlow<BooksUiState> = _uiState.asStateFlow()

    private val disposables = CompositeDisposable()

    fun loadBooks() {
        _uiState.value = BooksUiState.Loading
        disposables.add(
            booksRepository.getBooks()
                .observeOn(Schedulers.trampoline())
                .subscribe(
                    { result -> handleResult(result) },
                    { error -> handleError(error) }
                )
        )
    }

    private fun handleResult(result: NetworkResult<Books>) {
        _uiState.value = when (result) {
            is NetworkResult.Success -> BooksUiState.Success(result.data.toUiModel())
            is NetworkResult.Error -> BooksUiState.Error(result.message)
        }
    }

    private fun handleError(throwable: Throwable) {
        _uiState.value = BooksUiState.Error(
            throwable.message ?: "Unknown error occurred"
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}