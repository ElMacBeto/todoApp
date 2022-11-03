package com.practica.todoapp.domain

import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.WebRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import javax.inject.Inject

class GetTasksListFromAPIUseCase {

    @Inject
    lateinit var webRepository: WebRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    operator fun invoke(success: (List<TaskEntity>) -> Unit,
                        failure: (String) -> Unit){
        webRepository.getTaskList(success, failure)
    }

}