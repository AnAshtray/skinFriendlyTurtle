package com.sunguard.di

import com.sunguard.data.remote.OpenMeteoApi
import com.sunguard.data.repository.ApplicationLogRepositoryImpl
import com.sunguard.data.repository.UvRepositoryImpl
import com.sunguard.domain.repository.ApplicationLogRepository
import com.sunguard.domain.repository.UvRepository
import com.sunguard.domain.usecase.ApplySunscreenUseCase
import com.sunguard.domain.usecase.GetApplicationLogUseCase
import com.sunguard.domain.usecase.GetUvSnapshotUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                },
            )
            .build()

    @Provides
    @Singleton
    fun provideOpenMeteoApi(client: OkHttpClient): OpenMeteoApi =
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoApi::class.java)

    @Provides
    @Singleton
    fun provideApplicationLogRepository(impl: ApplicationLogRepositoryImpl): ApplicationLogRepository = impl

    @Provides
    @Singleton
    fun provideUvRepository(impl: UvRepositoryImpl): UvRepository = impl

    @Provides
    fun provideApplySunscreenUseCase(repository: ApplicationLogRepository): ApplySunscreenUseCase =
        ApplySunscreenUseCase(repository)

    @Provides
    fun provideGetApplicationLogUseCase(repository: ApplicationLogRepository): GetApplicationLogUseCase =
        GetApplicationLogUseCase(repository)

    @Provides
    fun provideGetUvSnapshotUseCase(repository: UvRepository): GetUvSnapshotUseCase =
        GetUvSnapshotUseCase(repository)
}
