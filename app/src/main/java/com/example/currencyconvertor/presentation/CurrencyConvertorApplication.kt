package com.example.currencyconvertor.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrencyConvertorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}