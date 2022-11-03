package com.practica.todoapp.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.practica.todoapp.R
import java.text.SimpleDateFormat
import java.util.*

class MyTimePickerDialog
    (val listener: (minutes: Int, hours: Int) -> Unit):
    DialogFragment(), TimePickerDialog.OnTimeSetListener  {

    override fun onTimeSet(p0: TimePicker?, minutes: Int, hours: Int) {
        listener(hours, minutes)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c: Calendar = Calendar.getInstance()

        val hours = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity as Context,
            R.style.my_dialog_time_theme, this, hours, minute, true)
    }

    companion object {
        const val TAG = "TimeDialog"
    }

}