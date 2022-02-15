package com.example.prmanager.router

import com.example.prmanager.data.remote.api.PullRequestsService
import com.example.prmanager.data.remote.api.UserService
import com.example.prmanager.utils.Constants.Companion.API_ENDPOINT
import com.example.prmanager.utils.Constants.Companion.PR_DATE_FORMAT
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppRouter {

  @Singleton
  @Provides
  fun provideHttpClient() : OkHttpClient {
    val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    return OkHttpClient.Builder()
      .addInterceptor(logger)
      .build()
  }

  @Singleton
  @Provides
  fun provideConverterFactory(): GsonConverterFactory {

    val gson = GsonBuilder().setDateFormat(PR_DATE_FORMAT).create()
    return GsonConverterFactory.create(gson)
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
    return Retrofit.Builder()
      .baseUrl(API_ENDPOINT)
      .client(okHttpClient)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Singleton
  @Provides
  fun providePRService(retrofit: Retrofit) : PullRequestsService {
    return retrofit.create(PullRequestsService::class.java)
  }

  @Singleton
  @Provides
  fun provideUserService(retrofit: Retrofit) : UserService {
    return retrofit.create(UserService::class.java)
  }

}