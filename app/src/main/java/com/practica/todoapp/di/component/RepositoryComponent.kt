package com.practica.todoapp.di.component

import com.practica.todoapp.di.module.RepositoryModule
import com.practica.todoapp.domain.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun inject(getAllTaskUseCase: GetAllTaskUseCase)
    fun inject(addTaskUseCase: AddOrUpdateTaskUseCase)
    fun inject(deleteTaskUseCase: DeleteTaskUseCase)
    fun inject(setNotificationUseCase: SetNotificationUseCase)
    fun inject(getTaskByIdUseCase: GetTaskByIdUseCase)
    fun inject(searchTaskUseCase: SearchTaskUseCase)
    fun inject(getTasksListFromAPI: GetTasksListFromAPIUseCase)

}