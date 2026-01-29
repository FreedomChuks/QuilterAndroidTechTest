package com.freedom.network.di

import com.freedom.network.retrofit.BookApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton
import com.freedom.network.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit


private val CONTENT_TYPE = "application/json".toMediaType()

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingLevel = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = loggingLevel })
            .build()
    }

    @Provides
    @Singleton
    fun provideBookApiService(
        json: Json,
        client: OkHttpClient,
    ): BookApiServices =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(BookApiServices::class.java)
}