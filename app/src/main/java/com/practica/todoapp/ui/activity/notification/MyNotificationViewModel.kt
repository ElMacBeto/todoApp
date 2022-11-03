package com.practica.todoapp.ui.activity.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerUseCaseComponent
import com.practica.todoapp.domain.GetTaskByIdUseCase
import com.practica.todoapp.domain.SetNotificationUseCase
import com.practica.todoapp.ui.util.notificaciones.Notifications
import kotlinx.coroutines.launch
import javax.inject.Inject


class MyNotificationViewModel : ViewModel() {

    @Inject
    lateinit var setNotificationUseCase: SetNotificationUseCase

    @Inject
    lateinit var getTaskByIdUseCase: GetTaskByIdUseCase

    var repository = LocalRepository()

    val taskNotification = MutableLiveData<TaskEntity>()
    val changeActivity = MutableLiveData<Int>()
    val finishNotificationActivity = MutableLiveData<Boolean>()

    init {
        DaggerUseCaseComponent.builder().build().inject(this)
    }

    fun getTask(id: Int) {
        viewModelScope.launch {
            val task = getTaskByIdUseCase(id)
            taskNotification.postValue(task)
        }
    }

    fun posponerNotification(task: TaskEntity) {
        Notifications().cancelNotification(task)
        Notifications().setNotification(task, true)
        finishNotificationActivity.postValue(true)
    }

    fun taskDone(task: TaskEntity) {
        viewModelScope.launch {
            task.status = 1
            changeActivity.postValue(repository.updateTask(task))
        }
    }


}