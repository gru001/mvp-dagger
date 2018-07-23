package com.example.pranit.mvp_dagger.di.application

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideAppilcation() = application
}