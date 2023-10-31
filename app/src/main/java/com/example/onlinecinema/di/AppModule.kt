package com.example.onlinecinema.di

import com.example.onlinecinema.App
import com.example.onlinecinema.data.networking.api.FilmsApi
import com.example.onlinecinema.data.repository.RepositoryImpl
import com.example.onlinecinema.domain.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext : App) {

    @Singleton
    @Provides
    fun provideRepository(filmsApi: FilmsApi): Repository {
        return RepositoryImpl(filmsApi)
    }

}