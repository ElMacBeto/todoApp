package com.practica.todoapp.ui.dialog.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.practica.todoapp.R
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.model.ErrorTaskMessage
import com.practica.todoapp.databinding.BottomSheetMenuBinding


class ModalBottomSheet(
    private val currentTask:TaskEntity?,
    private val function: () -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel: ModalBottomViewModel by viewModels()
    private lateinit var binding: BottomSheetMenuBinding
    private var editFlag=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //this forces the sheet to appear at max height even on landscape
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog_Rounded
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (currentTask != null) {
            binding.task=currentTask
            editFlag=true
        } else {
            binding.task = TaskEntity(null)
        }

        setDropdownMenu()
        setListeners()
        setObservers()
    }

    private fun setDropdownMenu() {
        val items = listOf("Job", "Family", "Home", "Sport", "Hobby", "Hand out")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list_dropdown_menu, items)
        (binding.taskTypeLy.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setListeners() {
        binding.taskDate.setOnClickListener { viewModel.getDate(parentFragmentManager) }
        binding.taskTime.setOnClickListener { viewModel.getTime(parentFragmentManager) }
        binding.addBtn.setOnClickListener { viewModel.addTask(getTask(), editFlag) }
    }

    private fun getTask(): TaskEntity {
        with(binding){
            val id = if (editFlag) currentTask!!.id else null
            return TaskEntity(
                id,
                taskName.text.toString(),
                taskDescription.text.toString(),
                taskType.text.toString(),
                taskDate.text.toString(),
                taskTime.text.toString(),
                notificationCode = (1..1024).shuffled().first()
            )
        }
    }

    private fun setObservers() {
        viewModel.valTime.observe(viewLifecycleOwner) {
            binding.taskTime.setText(it)
        }
        viewModel.valDate.observe(viewLifecycleOwner) {
            binding.taskDate.setText(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            setErrorsMessage(it)
        }
        viewModel.message.observe(viewLifecycleOwner){
            function()
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun setErrorsMessage(errorMessage: ErrorTaskMessage?) {
        with(binding){
            taskName.error= errorMessage!!.name
            taskDescription.error=errorMessage.description
            taskType.error = errorMessage.type
            taskDate.error = errorMessage.date
            taskTime.error = errorMessage.time
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}