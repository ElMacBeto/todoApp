package com.practica.todoapp.ui.dialog.addtask

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.model.ErrorTaskMessage
import com.practica.todoapp.di.component.DaggerUseCaseComponent
import com.practica.todoapp.domain.AddOrUpdateTaskUseCase
import com.practica.todoapp.domain.SetNotificationUseCase
import com.practica.todoapp.ui.dialog.MyDatePickerDialog
import com.practica.todoapp.ui.dialog.MyTimePickerDialog
import kotlinx.coroutines.launch
import javax.inject.Inject

class ModalBottomViewModel : ViewModel() {

    @Inject
    lateinit var addTaskUseCase: AddOrUpdateTaskUseCase

    @Inject
    lateinit var setNotificationUseCase: SetNotificationUseCase

    init {
        DaggerUseCaseComponent.builder().build().inject(this)
    }

    val valDate = MutableLiveData<String>()
    val valTime = MutableLiveData<String>()
    val errorMessage = MutableLiveData<ErrorTaskMessage>()
    val message = MutableLiveData<String>()

    fun getDate(fragmentManager: FragmentManager) {
        val datePicker = MyDatePickerDialog { day, month, year ->
            valDate.postValue("$year-${String.format("%02d", month)}-${String.format("%02d", day)}")
        }
        datePicker.show(fragmentManager, MyDatePickerDialog.TAG)
    }

    fun getTime(fragmentManager: FragmentManager) {
        val datePicker = MyTimePickerDialog { minute, hour ->
            valTime.postValue("${String.format("%02d", hour)}:${String.format("%02d", minute)}")
        }
        datePicker.show(fragmentManager, MyTimePickerDialog.TAG)
    }

    fun addTask(task: TaskEntity, editFlag: Boolean) {
        viewModelScope.launch {
            addTaskUseCase(task, editFlag) {currentErrorMessage, taskId->
                if (currentErrorMessage.status) {
                    errorMessage.postValue(currentErrorMessage)
                } else {
                    task.id = taskId
                    val successNotification = setNotificationUseCase(task)
                    if (successNotification) message.postValue("task added successfully")
                    else message.postValue("error to set notificacition")
                }
            }
        }
    }


}