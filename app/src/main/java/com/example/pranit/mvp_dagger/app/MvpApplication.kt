package com.example.pranit.mvp_dagger.app

import android.app.Application
import com.example.pranit.mvp_dagger.di.application.ApplicationComponent
import com.example.pranit.mvp_dagger.di.application.ApplicationModule
import com.example.pranit.mvp_dagger.di.application.DaggerApplicationComponent

class MvpApplication : Application(){

    private var mApplicationComponent: ApplicationComponent ?= null

    override fun onCreate() {
        getApplicationComponent()
        super.onCreate()

    }

    private fun getApplicationComponent() : ApplicationComponent{
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .build()
        }

        return mApplicationComponent!!
    }
}