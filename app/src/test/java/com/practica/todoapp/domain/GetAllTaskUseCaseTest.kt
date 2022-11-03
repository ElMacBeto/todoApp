package com.practica.todoapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.internal.concurrent.Task
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class GetAllTaskUseCaseTest{


    @RelaxedMockK
    private lateinit var repository: LocalRepository

    lateinit var getAllTaskUseCase: GetAllTaskUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getAllTaskUseCase= GetAllTaskUseCase()
    }

    @Test
    fun whenDatabaseReturnValues() = runBlocking{
        //Given
        Assert.assertFalse(getAllTaskUseCase().value!!.isEmpty())

    }



}