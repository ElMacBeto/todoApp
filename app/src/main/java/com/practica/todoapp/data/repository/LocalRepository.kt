package com.practica.todoapp.data.repository

import com.practica.todoapp.MainApplication.Companion.database
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import javax.inject.Inject

class LocalRepository {

    private val db = database.taskDao()

    fun getAllTask() = db.getAll()

    suspend fun getById(id:Int):TaskEntity{
        return db.getById(id)
    }

    suspend fun saveTask(task:TaskEntity):Long{
        return db.add(task)
    }

    suspend fun deleteTask(task:TaskEntity){
        return db.delete(task)
    }

    suspend fun deleteTaskTable(){
        db.deleteTable()
    }

    suspend fun updateTask(task:TaskEntity):Int{
        return db.update(task)
    }

    fun searchTask(query:String) =  db.search(query)


}