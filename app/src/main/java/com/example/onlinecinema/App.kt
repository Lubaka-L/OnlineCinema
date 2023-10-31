package com.example.onlinecinema

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.onlinecinema.di.AppComponent
import com.example.onlinecinema.di.AppModule
import com.example.onlinecinema.di.DaggerAppComponent
import com.example.onlinecinema.domain.repository.Repository
import com.example.onlinecinema.ui.startScreen.StartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

}