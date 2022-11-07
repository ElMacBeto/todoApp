package com.practica.todoapp.domain

import com.practica.todoapp.data.datasource.database.entity.TAST_STATUS_DELETE
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import javax.inject.Inject

class DeleteTaskUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    suspend operator fun invoke(task: TaskEntity, function: () -> Unit) {
        task.status = TAST_STATUS_DELETE
        repository.updateTask(task)
        function()
    }


}