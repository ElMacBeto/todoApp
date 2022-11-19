package com.practica.todoapp.ui.util

import java.util.*


class ValidateDate {

    operator fun invoke(date: String, time: String): Boolean {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hours = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        date.split('-').also {
            if (it[0].toInt() < year)
                return false
            if (it[0].toInt() > year)
                return true
            else {
                if (it[1].toInt() < month)
                    return false
                if (it[1].toInt() > month)
                    return true
                else {
                    if (it[2].toInt() < day)
                        return false
                    if (it[2].toInt() > day)
                        return true
                }
            }
        }

        time.split(':').also {
            if (it[0].toInt() < hours)
                return false
            if (it[0].toInt() > hours)
                return true
            else {
                if (it[1].toInt() < minute)
                    return false
                return it[1].toInt() >= minute
            }
        }
    }
}