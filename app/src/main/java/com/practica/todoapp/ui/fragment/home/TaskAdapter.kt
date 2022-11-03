package com.practica.todoapp.ui.fragment.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import androidx.recyclerview.widget.RecyclerView
import com.practica.todoapp.R
import com.practica.todoapp.data.datasource.database.entity.*
import com.practica.todoapp.databinding.ItemTaskBinding


class TaskAdapter:
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var taskList = mutableListOf<TaskEntity>()
    var onDeleteItem:((TaskEntity, View)-> Unit)? = null
    var onEditItem:((TaskEntity) -> Unit)? = null
    var onDoneItem:((TaskEntity) -> Unit)? = null
    var onInfoItem:((TaskEntity) -> Unit)? = null
    private var lastPosition = -1


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTaskBinding.bind(view)

        fun setView(task:TaskEntity){
            itemView.visibility=View.VISIBLE
            itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

            with(binding){
                itemTime.text= task.date + " " + task.time
                itemTitle.text=task.name
                motionContainer.transitionToStart()
            }
        }

        fun setColorType(type:String, status:Int){
            when(type){
                TASK_TYPE_FAMILY -> {
                    binding.itemType.setBackgroundColor(itemView.context.getColor(R.color.family))
                    binding.fabMenu.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_family))
                }
                TASK_TYPE_JOB -> {
                    binding.itemType.setBackgroundColor(itemView.context.getColor(R.color.job))
                    binding.fabMenu.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_job))
                }
                TASK_TYPE_HOME -> {
                    binding.itemType.setBackgroundColor(itemView.context.getColor(R.color.home_task))
                    binding.fabMenu.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_home))
                }
                TASK_TYPE_SPORT -> {
                    binding.itemType.setBackgroundColor(itemView.context.getColor(R.color.sport))
                    binding.fabMenu.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_sport))
                }
                TASK_TYPE_HOBBY -> {
                    binding.itemType.setBackgroundColor(itemView.context.getColor(R.color.hobby))
                    binding.fabMenu.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_hobbie))
                }
                TASK_TYPE_HANDOUT -> {
                    binding.itemType.setBackgroundColor(itemView.context.getColor(R.color.handout))
                    binding.fabMenu.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_handout))
                }
            }

            if (status == TAST_STATUS_DONE){
                binding.itemContainer.setCardBackgroundColor(R.color.black)
            }

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_task, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val task = taskList[position]

        setAnimation(viewHolder.itemView, position)

        with(viewHolder){
            setView(task)
            setColorType(task.type, task.status)
            binding.fabDelete.setOnClickListener{ onDeleteItem?.invoke(task, itemView) }
            binding.fabEdit.setOnClickListener{ onEditItem?.invoke(task) }
            binding.itemContainer.setOnClickListener{ onInfoItem?.invoke(task) }
            binding.fabDone.setOnClickListener{ onDoneItem?.invoke(task) }
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun getItemCount() = taskList.size
}