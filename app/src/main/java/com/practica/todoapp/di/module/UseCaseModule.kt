package com.practica.todoapp.di.module

import com.practica.todoapp.domain.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    fun providegetAllTaskUseCase(): GetAllTaskUseCase {
        return GetAllTaskUseCase()
    }

    @Provides
    fun provideAddTaskUseCase(): AddOrUpdateTaskUseCase {
        return AddOrUpdateTaskUseCase()
    }

    @Provides
    fun provideDeleteTaskUseCase(): DeleteTaskUseCase {
        return DeleteTaskUseCase()
    }

    @Provides
    fun provideSetNotificationUseCase(): SetNotificationUseCase {
        return SetNotificationUseCase()
    }

    @Provides
    fun provideGetTaskByIdUseCase(): GetTaskByIdUseCase {
        return GetTaskByIdUseCase()
    }

    @Provides
    fun provideSearchTaskUseCase(): SearchTaskUseCase {
        return SearchTaskUseCase()
    }

    @Provides
    fun provideGetTasksListFromAPIUseCase(): GetTasksListFromAPIUseCase {
        return GetTasksListFromAPIUseCase()
    }

}