package com.example.pranit.mvp_dagger.di.application

import com.example.pranit.mvp_dagger.app.MvpApplication
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(mvpApp : MvpApplication)
}