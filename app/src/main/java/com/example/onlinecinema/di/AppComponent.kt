package com.example.onlinecinema.di

import com.example.onlinecinema.ui.MainActivity
import com.example.onlinecinema.ui.film.FilmViewModel
import com.example.onlinecinema.ui.filmCollections.FilmCollectonsViewModel
import com.example.onlinecinema.ui.startScreen.StartViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule.NetWorkModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(filmViewModel: FilmViewModel)
    fun inject(filmCollectionsViewModel : FilmCollectonsViewModel)
    fun inject(startViewModel : StartViewModel)

}