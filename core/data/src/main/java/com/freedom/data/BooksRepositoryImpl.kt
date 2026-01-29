package com.freedom.data

import com.freedom.common.NetworkResult
import com.freedom.data.mapper.toDomainModel
import com.freedom.domain.BooksRepository
import com.freedom.model.Books
import com.freedom.network.NetworkDatasource
import com.freedom.network.safeApiCall
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val networkDatasource: NetworkDatasource
): BooksRepository {
    override fun getBooks(): Single<NetworkResult<Books>> = safeApiCall(
        apiCall = { networkDatasource.getBooks() },
        dataMapper = { it.toDomainModel()}
    )

}