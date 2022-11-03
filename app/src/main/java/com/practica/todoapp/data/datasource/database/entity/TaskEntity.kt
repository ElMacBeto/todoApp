package com.practica.todoapp.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val TAST_STATUS_PENDING = 0
const val TAST_STATUS_DONE = 1
const val TAST_STATUS_DELETE = 2

const val TASK_TYPE_JOB = "Job"
const val TASK_TYPE_FAMILY = "Family"
const val TASK_TYPE_HOME = "Home"
const val TASK_TYPE_SPORT = "Sport"
const val TASK_TYPE_HOBBY = "Hobby"
const val TASK_TYPE_HANDOUT = "Hand out"

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val date: String = "",
    val time: String = "",
    var notificationCode: Int = 0,
    var status: Int = TAST_STATUS_PENDING
)
