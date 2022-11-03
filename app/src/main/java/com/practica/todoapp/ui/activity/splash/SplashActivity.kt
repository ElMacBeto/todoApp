package com.practica.todoapp.ui.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import com.practica.todoapp.R
import com.practica.todoapp.databinding.ActivitySplashBinding
import com.practica.todoapp.di.component.DaggerUseCaseComponent
import com.practica.todoapp.domain.GetTasksListFromAPIUseCase
import com.practica.todoapp.ui.activity.main.MainActivity
import com.practica.todoapp.ui.util.InternetConnection.isConnectToInternet
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: MySplashViewModel by viewModels()

    @Inject
    lateinit var getTasksListFromAPIUseCase: GetTasksListFromAPIUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerUseCaseComponent.builder().build().inject(this)

        setObserver()
        binding.animation.animationView.setAnimation(R.raw.splash_task)
        if (isConnectToInternet())
            viewModel.downloadTaskList()
        else
            startMainActivity()
    }

    private fun setObserver() {
        viewModel.tasksList.observe(this) {
            viewModel.addTasksList(it)
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.finishActivity.observe(this) {
           startMainActivity()
        }
    }

    private fun startMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_from_rigth,R.anim.exit_from_left)
            finish()

        }, 2000)
    }


}