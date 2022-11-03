package com.practica.todoapp.di.component

import android.content.Context
import com.practica.todoapp.di.module.UtilModule

import dagger.Component

import javax.inject.Singleton

@Singleton
@Component(modules = [UtilModule::class])
interface UtilComponent {
    val appContext: Context?
}