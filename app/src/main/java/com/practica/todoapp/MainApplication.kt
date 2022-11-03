package com.practica.todoapp

import android.app.Application
import androidx.room.Room
import com.practica.todoapp.data.datasource.database.LocalDataBase
import com.practica.todoapp.di.component.DaggerUtilComponent
import com.practica.todoapp.di.component.UtilComponent
import com.practica.todoapp.di.module.ContextModule

class MainApplication:Application() {

    companion object {
        lateinit var database: LocalDataBase
        var utilComponent: UtilComponent? = null
    }

    override fun onCreate() {

        database =  Room.databaseBuilder(
            this, LocalDataBase::class.java,
            "task-db")
            .build()

        utilComponent = DaggerUtilComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .build()

        super.onCreate()

    }
}