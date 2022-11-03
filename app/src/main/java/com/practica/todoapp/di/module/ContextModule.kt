package com.practica.todoapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides


@Module
class ContextModule(con: Context) {

    //activity
    @get:Provides
    val appCompatActivity: AppCompatActivity? = null

    @get:Provides
    val fragment: Fragment? = null
    private val context: Context = con

    @Provides
    fun getContext(): Context {
        return context
    }

}