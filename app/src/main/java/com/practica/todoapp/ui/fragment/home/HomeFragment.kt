package com.practica.todoapp.ui.fragment.home


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.practica.todoapp.R
import com.practica.todoapp.data.datasource.database.entity.*
import com.practica.todoapp.databinding.FragmentHomeBinding
import com.practica.todoapp.ui.dialog.addtask.ModalBottomSheet
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    private lateinit var fabAdd: ExtendedFloatingActionButton
    private lateinit var rightArrowBtn: ImageButton
    private lateinit var leftArrowBtn: ImageButton
    private lateinit var currentDateTv:TextView
    private var taskList = mutableListOf<TaskEntity>()
    private var offsetValue:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fabAdd = requireActivity().findViewById(R.id.fab_add)
        currentDateTv = requireActivity().findViewById(R.id.current_date)
        leftArrowBtn = requireActivity().findViewById(R.id.left_arrow_btn)
        rightArrowBtn = requireActivity().findViewById(R.id.right_arrow_btn)

        requireActivity().findViewById<SearchView>(R.id.search)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isEmpty())
                        viewModel.getTaskList()
                    else
                        viewModel.search(newText)
                    return true
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
        initAdapter()
        setListener()
        swipeFunction()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getTaskList()
        }, 1000)

    }

    private fun setObserver() {
        viewModel.messaje.observe(viewLifecycleOwner) {
            showSnackBar(it)
        }

        viewModel.taskList.observe(viewLifecycleOwner) {
            taskList.clear()
            taskList.addAll(it)
            adapter.taskList = taskList
            adapter.notifyDataSetChanged()
            if (it.isEmpty())
                requireActivity().findViewById<LinearLayout>(R.id.animation_empty_list).visibility =
                    View.VISIBLE
            else
                requireActivity().findViewById<LinearLayout>(R.id.animation_empty_list).visibility =
                    View.GONE
        }

        viewModel.updateList.observe(viewLifecycleOwner) {
            viewModel.getTaskList()
        }
    }

    private fun initAdapter() {
        adapter = TaskAdapter()
        adapter.notifyDataSetChanged()
//        adapter.onDeleteItem = { task, item ->
//            viewModel.deleteTask(parentFragmentManager, task, item)
//        }
        adapter.onDoneItem = {
            viewModel.setDoneTask(parentFragmentManager, it)
        }
        adapter.onEditItem = {
            viewModel.updateTask(parentFragmentManager, it)
        }
        adapter.taskList = taskList
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.adapter = adapter
        binding.rvTask.adapter!!.notifyDataSetChanged()
    }

    private fun setListener() {
        fabAdd.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet(null) { viewModel.getTaskList() }
            modalBottomSheet.show(parentFragmentManager, ModalBottomSheet.TAG)
        }

        binding.rvTask.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy>0){
                    fabAdd.shrink()
                }else{
                    fabAdd.extend()
                }
            }
        })

        rightArrowBtn.setOnClickListener {
            offsetValue += 1
            setTvDate()
        }
        leftArrowBtn.setOnClickListener {
            offsetValue -= 1
            setTvDate()
        }
    }

    private fun swipeFunction() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedTask: TaskEntity =
                    taskList[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition
                viewModel.newDeleteTask(deletedTask)
                taskList.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(binding.rvTask, "Deleted " + deletedTask.name, Snackbar.LENGTH_LONG)
                    .setAction(
                        "ok"
                    ) {
                        taskList.add(position, deletedTask)
                        adapter.notifyItemInserted(position)
                    }.show()
            }
        }).attachToRecyclerView(binding.rvTask)
    }

    private fun showSnackBar(message:String){
        Snackbar.make(binding.rvTask, message, Snackbar.LENGTH_LONG)
            .setAction(
                "ok"
            ) {

            }.show()

    }

    private fun setTvDate() {
        val ldt: LocalDateTime = LocalDateTime.now().plusDays(offsetValue)
        val sdf =  DateTimeFormatter.ofPattern("EEE, MMM d, ''yy")
        val currentDate = sdf.format(ldt)
        Log.i("date", currentDate)
        currentDateTv.text = currentDate
    }



}