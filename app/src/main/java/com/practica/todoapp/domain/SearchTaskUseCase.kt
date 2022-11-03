package com.practica.todoapp.domain

import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import javax.inject.Inject

class SearchTaskUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    operator fun invoke(query:String)=
        repository.searchTask(query)

}