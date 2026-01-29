package com.freedom.data

import BookDto
import ReadingLogEntriesItem
import Work
import com.freedom.common.NetworkResult
import com.freedom.model.Books
import com.freedom.network.NetworkDatasource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class BooksRepositoryImplTest {

    private val mockNetworkDatasource = mockk<NetworkDatasource>()
    private lateinit var repository: BooksRepositoryImpl

    @Before
    fun setUp() {
        repository = BooksRepositoryImpl(mockNetworkDatasource)
    }

    @Test
    fun `getBooks returns Success with mapped data`() {
        val mockDto = BookDto(
            data = listOf(
                ReadingLogEntriesItem(
                    work = Work(
                        title = "Test Book",
                        authorNames = listOf("Test Author"),
                        firstPublishYear = 2024,
                        coverEditionKey = "test_cover"
                    )
                )
            )
        )

        every { mockNetworkDatasource.getBooks() } returns Single.just(mockDto)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        var result: NetworkResult<Books>? = null
        val disposable = repository.getBooks()
            .subscribe(
                { networkResult -> result = networkResult },
                { }
            )
        disposable.dispose()

        assert(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val books = successResult.data.books!!
        assert(books.isNotEmpty())
        assert(books.size == 1)
        assert(books[0].title == "Test Book")
        assert(books[0].authorNames == "Test Author")
    }


    @Test
    fun `getBooks maps multiple books correctly`() {
        val mockDto = BookDto(
            data = listOf(
                ReadingLogEntriesItem(
                    work = Work(
                        title = "Book 1",
                        authorNames = listOf("Author 1", "Author 2"),
                        firstPublishYear = 2024,
                        coverEditionKey = "cover1"
                    )
                ),
                ReadingLogEntriesItem(
                    work = Work(
                        title = "Book 2",
                        authorNames = listOf("Author 3"),
                        firstPublishYear = 2023,
                        coverEditionKey = "cover2"
                    )
                )
            )
        )

        every { mockNetworkDatasource.getBooks() } returns Single.just(mockDto)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        var result: NetworkResult<Books>? = null
        val disposable = repository.getBooks()
            .subscribe(
                { networkResult -> result = networkResult },
                { }
            )
        disposable.dispose()

        assert(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val books = successResult.data.books!!
        assert(books.size == 2)
        assert(books[0].title == "Book 1")
        assert(books[1].title == "Book 2")
    }

    @Test
    fun `getBooks filters out null work entries`() {
        val mockDto = BookDto(
            data = listOf(
                ReadingLogEntriesItem(work = null),
                ReadingLogEntriesItem(
                    work = Work(
                        title = "Valid Book",
                        authorNames = listOf("Author"),
                        firstPublishYear = 2024,
                        coverEditionKey = "cover"
                    )
                )
            )
        )

        every { mockNetworkDatasource.getBooks() } returns Single.just(mockDto)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        var result: NetworkResult<Books>? = null
        val disposable = repository.getBooks()
            .subscribe(
                { networkResult -> result = networkResult },
                { }
            )
        disposable.dispose()

        assert(result is NetworkResult.Success)
        val books = (result as NetworkResult.Success).data
        assert(books.books!!.size == 1)
    }

    @Test
    fun `getBooks calls datasource exactly once`() {
        every { mockNetworkDatasource.getBooks() } returns Single.just(
            BookDto(data = emptyList())
        ).subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        val disposable = repository.getBooks()
            .subscribe({ }, { })
        disposable.dispose()

        verify(exactly = 1) { mockNetworkDatasource.getBooks() }
    }

    @Test
    fun `getBooks handles empty data list`() {
        val mockDto = BookDto(data = emptyList())
        every { mockNetworkDatasource.getBooks() } returns Single.just(mockDto)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        var result: NetworkResult<Books>? = null
        val disposable = repository.getBooks()
            .subscribe(
                { networkResult -> result = networkResult },
                { }
            )
        disposable.dispose()

        assert(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val books = successResult.data.books!!
        assert(books.isEmpty())
    }
}
