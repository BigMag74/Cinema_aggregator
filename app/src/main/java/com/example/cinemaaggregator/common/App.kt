package com.example.cinemaaggregator.common

import android.app.Application
import androidx.fragment.app.Fragment
import com.example.cinemaaggregator.common.di.AppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
}

fun Fragment.getAppComponent(): AppComponent =
    (requireContext() as App).appComponent