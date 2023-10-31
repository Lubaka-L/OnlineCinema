package com.example.onlinecinema.di

import com.example.onlinecinema.data.networking.api.FilmsApi
import com.example.onlinecinema.data.networking.serialization.adapters.*
import com.example.onlinecinema.domain.models.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://kinopoiskapiunofficial.tech"

class NetworkModule {

    @Module
    class NetWorkModule {

        @Provides
        fun provideInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        }

        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .registerTypeAdapter(Film::class.java, FilmBodyAdapter())
                .registerTypeAdapter(SequelsPrequels::class.java, SequelsPrequelsBodyAdapter())
                .registerTypeAdapter(Budget::class.java, BudgetBodyAdapter())
                .registerTypeAdapter(Director::class.java, DirectorBodyAdapter())
                .setLenient()
                .create()
        }

        @Provides
        fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
            return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
        }

        @Provides
        fun provideApi(retrofit: Retrofit.Builder): FilmsApi {
            return retrofit.baseUrl(BASE_URL).build().create(FilmsApi::class.java)
        }
    }
}