package com.stalwart.marvel.di

import com.stalwart.data.characters.api.CharactersApiHelper
import com.stalwart.data.characters.api.CharactersApiHelperImpl
import com.stalwart.data.characters.api.CharactersApiService
import com.stalwart.data.details.api.DetailsApiHelper
import com.stalwart.data.details.api.DetailsApiHelperImpl
import com.stalwart.data.details.api.DetailsApiService
import com.stalwart.domain.usecase.characters.GetCharactersUseCase
import com.stalwart.domain.usecase.characters.GetCharactersUseCaseImpl
import com.stalwart.domain.usecase.details.GetCharacterDetailsUseCase
import com.stalwart.domain.usecase.details.GetCharacterDetailsUseCaseImpl
import com.stalwart.marvel.BuildConfig
import com.stalwart.marvel.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
Created by Swanand Deshpande
 */

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = AppConstants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String) : Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCharactersApiService(retrofit: Retrofit) = retrofit.create(CharactersApiService::class.java)

    @Provides
    @Singleton
    fun provideCharacterApiHelper(apiHelperImpl: CharactersApiHelperImpl): CharactersApiHelper = apiHelperImpl

    @Provides
    @Singleton
    fun provideCharacterUseCaseHelper(useCaseHelperImpl: GetCharactersUseCaseImpl): GetCharactersUseCase = useCaseHelperImpl

    @Provides
    @Singleton
    fun provideDetailsApiService(retrofit: Retrofit) = retrofit.create(DetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideDetailsApiHelper(apiHelperImpl: DetailsApiHelperImpl): DetailsApiHelper = apiHelperImpl

    @Provides
    @Singleton
    fun provideDetailsUseCaseHelper(useCaseHelperImpl: GetCharacterDetailsUseCaseImpl): GetCharacterDetailsUseCase = useCaseHelperImpl
}