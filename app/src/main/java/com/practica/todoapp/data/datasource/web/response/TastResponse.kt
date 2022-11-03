package com.practica.todoapp.data.datasource.web.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.practica.todoapp.data.datasource.database.entity.TaskEntity

data class TastResponse(
    @Expose
    @SerializedName("status") val status: String?,
    @Expose
    @SerializedName("message") val message: String? ,
    @Expose
    @SerializedName("tasks") val tasks: List<TaskEntity>
)
