package com.freedom.network.di

import com.freedom.network.NetworkDataSourceImpl
import com.freedom.network.NetworkDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun bindNetworkDatasource(
        impl: NetworkDataSourceImpl,
    ): NetworkDatasource
}