package com.practica.todoapp.domain

import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import javax.inject.Inject

class GetAllTaskUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    suspend operator fun invoke(date:String)=
       repository.getAllTask(date)





}