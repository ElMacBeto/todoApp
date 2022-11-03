package com.practica.todoapp.data.datasource.web

import com.practica.todoapp.data.datasource.web.response.TastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("/tasks")
    suspend fun getTasksList():Response<TastResponse>


}