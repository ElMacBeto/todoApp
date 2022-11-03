package com.practica.todoapp.data.repository

import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.datasource.web.WebService
import com.practica.todoapp.di.component.DaggerFrameworkComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WebRepository {

    @Inject
    lateinit var webService: WebService

    init {
        DaggerFrameworkComponent.builder().build().inject(this)
    }

    fun getTaskList(
        function: (List<TaskEntity>) -> Unit,
        errorFunction: (String) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = webService.getTasksList()

                if (response.isSuccessful) {
                    val taskResponse = response.body()
                    if (taskResponse!!.status == "success") {
                        function(taskResponse.tasks!!)
                    } else {
                        errorFunction(taskResponse.message!!)
                    }
                } else {
                    errorFunction(response.message())
                }
            } catch (exception: Exception) {
                errorFunction(exception.message!!)
            }
        }
    }

}