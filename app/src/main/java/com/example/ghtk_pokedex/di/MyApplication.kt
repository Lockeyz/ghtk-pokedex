package com.example.ghtk_pokedex.di

import android.app.Application

class MyApplication: Application() {

    private lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}