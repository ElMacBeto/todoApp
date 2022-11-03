package com.practica.todoapp.di.component

import com.practica.todoapp.data.repository.WebRepository
import com.practica.todoapp.di.module.WebModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WebModule::class])
interface FrameworkComponent {

    fun inject(webRepository: WebRepository)

}