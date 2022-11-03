package com.practica.todoapp.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.practica.todoapp.R
import com.practica.todoapp.databinding.ActivityMainBinding
import com.practica.todoapp.ui.fragment.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContainer()
        setTvDate()
    }

    private fun setTvDate() {
        val sdf = SimpleDateFormat("EEE, MMM d, ''yy")
        val currentDate = sdf.format(Date())
        Log.i("date",currentDate)
        binding.currentDate.text=currentDate
    }

    private fun setContainer() {
        val fragment = HomeFragment()
        val transition = supportFragmentManager.beginTransaction()
        transition.replace(R.id.container, fragment)
        transition.commit()
    }


}