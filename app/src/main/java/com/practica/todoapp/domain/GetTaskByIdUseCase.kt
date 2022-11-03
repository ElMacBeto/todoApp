package com.practica.todoapp.domain

import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import javax.inject.Inject

class GetTaskByIdUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    suspend operator fun invoke(id:Int):TaskEntity{
        return repository.getById(id)
    }

}