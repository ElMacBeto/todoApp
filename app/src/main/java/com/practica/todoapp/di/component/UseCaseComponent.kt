package com.practica.todoapp.di.component

import com.practica.todoapp.di.module.UseCaseModule
import com.practica.todoapp.ui.activity.notification.MyNotificationViewModel
import com.practica.todoapp.ui.activity.splash.MySplashViewModel
import com.practica.todoapp.ui.activity.splash.SplashActivity
import com.practica.todoapp.ui.dialog.addtask.ModalBottomViewModel
import com.practica.todoapp.ui.fragment.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UseCaseModule::class])
interface UseCaseComponent {

    fun inject(homeViewModel: HomeViewModel)
    fun inject(modalBottomViewModel: ModalBottomViewModel)
    fun inject(myNotificationViewModel: MyNotificationViewModel)
    fun inject(splashActivity: SplashActivity)
    fun inject(mySplashViewModel: MySplashViewModel)

}