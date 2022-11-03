package com.practica.todoapp.ui.activity.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerUseCaseComponent
import com.practica.todoapp.domain.AddOrUpdateTaskUseCase
import com.practica.todoapp.domain.GetTaskByIdUseCase
import com.practica.todoapp.domain.GetTasksListFromAPIUseCase
import com.practica.todoapp.domain.SetNotificationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MySplashViewModel : ViewModel() {

    @Inject
    lateinit var getTaskByIdUseCase: GetTaskByIdUseCase

    @Inject
    lateinit var getTasksListFromAPIUseCase: GetTasksListFromAPIUseCase

    @Inject
    lateinit var addOrUpdateTaskUseCase: AddOrUpdateTaskUseCase

    @Inject
    lateinit var setNotificationUseCase: SetNotificationUseCase

    private val repository = LocalRepository()
    val tasksList = MutableLiveData<List<TaskEntity>>()
    val message = MutableLiveData<String>()
    val finishActivity = MutableLiveData<Boolean>()

    init {
        DaggerUseCaseComponent.builder().build().inject(this)
    }

    fun downloadTaskList() {
        viewModelScope.launch {
            getTasksListFromAPIUseCase(
                success = {
                    tasksList.postValue(it)
                },
                failure = {
                    message.postValue(it)
                })
        }
    }

    fun addTasksList(taskList: List<TaskEntity>) {
        viewModelScope.launch {
            repository.deleteTaskTable()

            taskList.forEach { task ->
                addOrUpdateTaskUseCase(task) { _, id ->
                    task.id = id
                    val successNotification = setNotificationUseCase(task)
                    if (!successNotification) message.postValue("error to set notificacition")
                }
            }

            finishActivity.postValue(true)
        }
    }


}