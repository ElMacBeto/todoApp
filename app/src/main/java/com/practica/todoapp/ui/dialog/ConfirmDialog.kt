package com.practica.todoapp.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.practica.todoapp.R
import com.practica.todoapp.databinding.DialogConfirmBinding

class ConfirmDialog(
    private val actionFunction: () -> Unit
) : DialogFragment()  {


    private var _binding: DialogConfirmBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        _binding = DialogConfirmBinding.inflate(LayoutInflater.from(context))


        binding.confirmMessage.text = "Do you want to change the task as done?"
        binding.title.text = "Confirm task"

        binding.positiveBtn.setOnClickListener {
            actionFunction()
            dismiss()
        }

        binding.negativeBtn.setOnClickListener {
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext(),R.style.MyMaterialAlertDialog)
            .setView(binding.root)
            .create()
    }

    companion object {
        const val TAG = "ConfirmDialog"
    }

}