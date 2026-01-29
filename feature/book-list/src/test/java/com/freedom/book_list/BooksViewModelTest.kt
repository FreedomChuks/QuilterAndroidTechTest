package com.freedom.book_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freedom.common.NetworkResult
import com.freedom.domain.BooksRepository
import com.freedom.model.Books
import com.freedom.model.BooksItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BooksViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<BooksRepository>()
    private lateinit var viewModel: BooksViewModel

    @Before
    fun setUp() {
        viewModel = BooksViewModel(mockRepository)
    }

    @Test
    fun `loadBooks starts with Loading state`() {
        every { mockRepository.getBooks() } returns Single.never()

        viewModel.loadBooks()

        assert(viewModel.uiState.value is BooksUiState.Loading)
    }

    @Test
    fun `loadBooks updates state to Success when repository returns data`() {
        val mockBooks = Books(
            books = listOf(
                BooksItem(
                    title = "Test Book",
                    authorNames = "Test Author",
                    firstPublishYear = 2024,
                    coverKey = "test_cover"
                )
            )
        )

        val result: NetworkResult<Books> = NetworkResult.Success(mockBooks)
        every { mockRepository.getBooks() } returns Single.just(result)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())


        viewModel.loadBooks()


        val state = viewModel.uiState.value
        assert(state is BooksUiState.Success)
        assert((state as BooksUiState.Success).ui.books.size == 1)
        assert(state.ui.books[0].title == "Test Book")
    }

    @Test
    fun `loadBooks updates state to Error when repository returns error`() {
        val errorMessage = "Network error"
        val result: NetworkResult<Books> = NetworkResult.Error(errorMessage)
        every { mockRepository.getBooks() } returns Single.just(result)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        viewModel.loadBooks()

        val state = viewModel.uiState.value
        assert(state is BooksUiState.Error)
        assert((state as BooksUiState.Error).message == errorMessage)
    }

    @Test
    fun `loadBooks updates state to Error when exception is thrown`() {
        val exception = Exception("API failure")
        every { mockRepository.getBooks() } returns Single.error<NetworkResult<Books>>(exception)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        viewModel.loadBooks()

        val state = viewModel.uiState.value
        assert(state is BooksUiState.Error)
        assert((state as BooksUiState.Error).message == "API failure")
    }

    @Test
    fun `loadBooks handles null exception message`() {
        every { mockRepository.getBooks() } returns Single.error<NetworkResult<Books>>(RuntimeException())
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        viewModel.loadBooks()

        val state = viewModel.uiState.value
        assert(state is BooksUiState.Error)
        assert((state as BooksUiState.Error).message == "Unknown error occurred")
    }

    @Test
    fun `repository is called when loadBooks is invoked`() {
        every { mockRepository.getBooks() } returns Single.never()

        viewModel.loadBooks()

        verify(exactly = 1) { mockRepository.getBooks() }
    }

    @Test
    fun `loadBooks can be called multiple times`() {
        val result: NetworkResult<Books> = NetworkResult.Error("Error")
        every { mockRepository.getBooks() } returns Single.just(result)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        viewModel.loadBooks()
        viewModel.loadBooks()
        viewModel.loadBooks()

        verify(exactly = 3) { mockRepository.getBooks() }
    }

    @Test
    fun `uiState flow emits correct sequence of states`() {
        val mockBooks = Books(books = emptyList())
        val result: NetworkResult<Books> = NetworkResult.Success(mockBooks)
        every { mockRepository.getBooks() } returns Single.just(result)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        assert(viewModel.uiState.value is BooksUiState.Loading)

        viewModel.loadBooks()

        assert(viewModel.uiState.value is BooksUiState.Success)
    }
}