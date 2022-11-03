package com.practica.todoapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practica.todoapp.data.datasource.database.dao.TaskDao
import com.practica.todoapp.data.datasource.database.entity.TaskEntity

@Database(
    entities =
    [
        TaskEntity::class,
    ],
    version = 1, exportSchema = false, )
abstract class LocalDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

}