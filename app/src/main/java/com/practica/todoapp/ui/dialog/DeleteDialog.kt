package com.practica.todoapp.ui.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.practica.todoapp.R
import com.practica.todoapp.databinding.DialogDeleteBinding

class DeleteDialog(
    private val message:String?,
    private val title:String?,
    private val function: () -> Unit
) : DialogFragment()  {

    private var _binding: DialogDeleteBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogDeleteBinding.inflate(LayoutInflater.from(context))


        binding.deleteMessage.text = message
        binding.title.text= title

        binding.positiveBtn.setOnClickListener {
            function()
            dismiss()
        }

        binding.negativeBtn.setOnClickListener { dismiss() }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }
    companion object {
        const val TAG = "DeleteDialog"
    }

}