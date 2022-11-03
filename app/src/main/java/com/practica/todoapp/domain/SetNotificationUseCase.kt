package com.practica.todoapp.domain

import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import com.practica.todoapp.ui.util.notificaciones.Notifications
import javax.inject.Inject

class SetNotificationUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init{
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    operator fun invoke(task: TaskEntity): Boolean {
        if (task.notificationCode!=0)
            Notifications().cancelNotification(task)
        return Notifications().setNotification(task)
    }

}