package com.practica.todoapp.ui.activity.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.practica.todoapp.R
import com.practica.todoapp.databinding.ActivityMainBinding
import com.practica.todoapp.ui.fragment.home.HomeFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var offsetValue: Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView?.visibility = View.GONE
        setContainer()
        setTvDate()
        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setTvDate() {
        val ldt: LocalDateTime = LocalDateTime.now().plusDays(offsetValue)
        val sdf = DateTimeFormatter.ofPattern("EEE, MMM d, ''yy")
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

    private fun setListeners() {
        with(binding) {
            calendarBtn?.setOnClickListener {
                if( calendarView?.visibility == View.GONE){
                    calendarView.visibility = View.VISIBLE
                    leftArrowBtn.visibility = View.GONE
                    rightArrowBtn.visibility = View.GONE
                }
                else{
                    calendarView?.visibility = View.GONE
                    leftArrowBtn.visibility = View.VISIBLE
                    rightArrowBtn.visibility = View.VISIBLE
                }
            }
        }
    }

}