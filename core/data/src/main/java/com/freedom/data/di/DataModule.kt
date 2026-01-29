package com.freedom.data.di

import com.freedom.data.BooksRepositoryImpl
import com.freedom.domain.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindBooksRepository(
        impl: BooksRepositoryImpl,
    ): BooksRepository
}
