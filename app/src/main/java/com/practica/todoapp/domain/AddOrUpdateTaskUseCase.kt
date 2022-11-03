package com.practica.todoapp.domain

import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.model.ErrorTaskMessage
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import javax.inject.Inject


class AddOrUpdateTaskUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    suspend operator fun invoke(
        task: TaskEntity,
        editFlag: Boolean=false,
        function: (error: ErrorTaskMessage, id: Int) -> Unit
    ) {

        val errorMessage = ErrorTaskMessage()

        if (task.name.length <= 3) {
            errorMessage.name = "name must have at least 3 characters"
            errorMessage.status = true
        }

        if (task.description.isEmpty()) {
            errorMessage.description = "description must not be empty"
            errorMessage.status = true
        }

        if (task.type.isEmpty()) {
            errorMessage.type = "type must not be empty"
            errorMessage.status = true
        }

        if (task.date.isEmpty()) {
            errorMessage.date = "select a day"
            errorMessage.status = true
        }

        if (task.time.isEmpty()) {
            errorMessage.time = "select time"
            errorMessage.status = true
        }

        if (!errorMessage.status) {
            var taskId = 0
            taskId = if (editFlag)
                repository.updateTask(task)
            else {
                repository.saveTask(task).toInt()
            }
            function(ErrorTaskMessage(), taskId)
        } else {
            function(errorMessage, -1)
        }
    }

}