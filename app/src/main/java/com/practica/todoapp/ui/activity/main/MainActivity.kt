package com.practica.todoapp.ui.activity.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.practica.todoapp.R
import com.practica.todoapp.databinding.ActivityMainBinding
import com.practica.todoapp.ui.fragment.home.HomeFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var offsetValue:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContainer()
        setTvDate()
    }

    private fun setTvDate() {
        val ldt: LocalDateTime = LocalDateTime.now().plusDays(offsetValue)
        val sdf =  DateTimeFormatter.ofPattern("EEE, MMM d, ''yy")
        val currentDate = sdf.format(ldt)
        Log.i("date", currentDate)
        binding.currentDate.text = currentDate
    }

    private fun setContainer() {
        val fragment = HomeFragment()
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container, fragment)
        transition.commit()
    }

}