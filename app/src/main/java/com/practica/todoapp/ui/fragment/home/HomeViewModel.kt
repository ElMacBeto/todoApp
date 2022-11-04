package com.practica.todoapp.ui.fragment.home

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practica.todoapp.MainApplication
import com.practica.todoapp.R
import com.practica.todoapp.data.datasource.database.entity.TAST_STATUS_DONE
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.di.component.DaggerUseCaseComponent
import com.practica.todoapp.domain.*
import com.practica.todoapp.ui.dialog.ConfirmDialog
import com.practica.todoapp.ui.dialog.DeleteDialog
import com.practica.todoapp.ui.dialog.addtask.ModalBottomSheet
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private val context = MainApplication.utilComponent!!.appContext

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
    val messaje = MutableLiveData<String>()
    val updateList = MutableLiveData<Boolean>()

    fun getTaskList() {
        viewModelScope.launch {
            val currentTaskList = getAllTaskUseCase()
            taskList.apply {
                addSource(currentTaskList) {
                    value = it
                }
            }
        }
    }

    fun deleteTask(fragmentManager: FragmentManager, task: TaskEntity, item: View) {
        val message = context!!.resources.getString(R.string.delete_message)
        val title = context.resources.getString(R.string.delete_title)

        val alert = DeleteDialog(message, title) {
//            deleteTaskUseCase(task, item){
//                messaje.postValue("task deleted")
//                updateList.postValue(true)
//            }
        }
        alert.show(fragmentManager, DeleteDialog.TAG)
    }

    fun updateTask(fragmentManager: FragmentManager, task: TaskEntity) {
        val modalBottomSheet = ModalBottomSheet(task) {
            getTaskList()
        }
        modalBottomSheet.show(fragmentManager, ModalBottomSheet.TAG)
    }

    fun setDoneTask(fragmentManager: FragmentManager, task: TaskEntity) {
        val alert = ConfirmDialog {
            viewModelScope.launch {
                task.status = TAST_STATUS_DONE
                addTaskUseCase(task, true) { _, _ ->
                    messaje.postValue("task done")
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

    fun newDeleteTask( task: TaskEntity) {
        viewModelScope.launch {
            deleteTaskUseCase(task) {
//                messaje.postValue("task deleted")
            }
        }
    }


}