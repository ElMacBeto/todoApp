package com.practica.todoapp.ui.fragment.home


import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.todoapp.data.datasource.database.entity.TAST_STATUS_DONE
import com.practica.todoapp.data.datasource.database.entity.TAST_STATUS_PENDING
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.di.component.DaggerUseCaseComponent
import com.practica.todoapp.domain.*
import com.practica.todoapp.ui.dialog.ConfirmDialog
import com.practica.todoapp.ui.dialog.addtask.ModalBottomSheet
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var getAllTaskUseCase: GetAllTaskUseCase

    @Inject
    lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Inject
    lateinit var addTaskUseCase: AddOrUpdateTaskUseCase

    @Inject
    lateinit var searchTaskUseCase: SearchTaskUseCase

    init {
        DaggerUseCaseComponent.builder().build().inject(this)
    }

    val taskList = MediatorLiveData<List<TaskEntity>>()
    val message = MutableLiveData<String>()
    val updateList = MutableLiveData<Boolean>()

    fun getTaskList(date:String) {

        Log.i("date viewmodel", date)
        viewModelScope.launch {
            val currentTaskList = getAllTaskUseCase(date)

            taskList.postValue(currentTaskList)
        }
    }

    fun updateTask(fragmentManager: FragmentManager, task: TaskEntity, date: String) {
        val modalBottomSheet = ModalBottomSheet(task) {
            getTaskList(date)
        }
        modalBottomSheet.show(fragmentManager, ModalBottomSheet.TAG)
    }

    fun setDoneTask(fragmentManager: FragmentManager, task: TaskEntity) {
        val alert = ConfirmDialog {
            viewModelScope.launch {
                task.status = TAST_STATUS_DONE
                addTaskUseCase(task, true) { _, _ ->
                    message.postValue("task done")
                    updateList.postValue(true)
                }
            }
        }
        alert.show(fragmentManager, ConfirmDialog.TAG)
    }

    fun search(query: String) {
        viewModelScope.launch {
            val currentList = searchTaskUseCase(query)
            taskList.apply {
                addSource(currentList) {
                    value = it
                }
            }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            deleteTaskUseCase(task) {
//                getTaskList(task.date)
            }
        }
    }




}