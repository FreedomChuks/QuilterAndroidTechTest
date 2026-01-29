package com.freedom.network

import BookDto
import ReadingLogEntriesItem
import Work
import com.freedom.network.retrofit.BookApiServices
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class NetworkDataSourceImplTest {

    private val mockApiService = mockk<BookApiServices>()
    private lateinit var datasource: NetworkDatasource

    @Before
    fun setUp() {
        datasource = NetworkDataSourceImpl(mockApiService)
    }

    @Test
    fun `getBooks calls api service`() {
        val mockDto = BookDto(data = emptyList())
        every { mockApiService.getBooks() } returns Single.just(mockDto)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        val disposable = datasource.getBooks()
            .subscribe({ }, { })
        disposable.dispose()

        verify(exactly = 1) { mockApiService.getBooks() }
    }

    @Test
    fun `getBooks returns api response`() {
        val mockDto = BookDto(
            data = listOf(
                ReadingLogEntriesItem(
                    work = Work(
                        title = "Test Book",
                        authorNames = listOf("Author"),
                        firstPublishYear = 2024,
                        coverEditionKey = "cover"
                    )
                )
            )
        )
        every { mockApiService.getBooks() } returns Single.just(mockDto)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        var result: BookDto? = null
        val disposable = datasource.getBooks()
            .subscribe { dto ->
                result = dto
            }
        disposable.dispose()

        assert(result == mockDto)
        assert(result?.data?.size == 1)
    }

    @Test
    fun `getBooks propagates api errors`() {
        val exception = Exception("API Error")
        every { mockApiService.getBooks() } returns Single.error<BookDto>(exception)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())

        var error: Throwable? = null
        val disposable = datasource.getBooks()
            .subscribe(
                { },
                { throwable -> error = throwable }
            )
        disposable.dispose()

        Thread.sleep(100)
        assert(error is Exception)
        assert(error?.message == "API Error")
    }
}