package com.practica.todoapp.ui.activity.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.databinding.ActivityMyNotificationBinding

const val FLAG_TASK_ID = "task_id"

class MyNotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyNotificationBinding
    private val viewModel: MyNotificationViewModel by viewModels()
    private var taskId: Number = 0
    private lateinit var task:TaskEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
        setListeners()
        taskId = intent.getIntExtra(FLAG_TASK_ID,0)
        if(taskId!=0)  viewModel.getTask(taskId as Int)
    }

    private fun setObserver(){
        viewModel.taskNotification.observe(this){ currentTask ->
            setView(currentTask)
            task=currentTask
        }

        viewModel.changeActivity.observe(this){
            finish()
        }

        viewModel.finishNotificationActivity.observe(this){
            finish()
        }
    }

    private fun setListeners(){
        binding.btnBackArrow.setOnClickListener{ viewModel.posponerNotification(task) }

        binding.aceptarBtn.setOnClickListener{ viewModel.posponerNotification(task) }

        binding.doneBtn.setOnClickListener{ viewModel.taskDone(task) }
    }

    private fun setView(currentTask:TaskEntity){
        binding.messageNotification.text = "Task to do ${currentTask.name} on ${currentTask.date}"
    }

    override fun onBackPressed() {
        viewModel.posponerNotification(task)
    }

}