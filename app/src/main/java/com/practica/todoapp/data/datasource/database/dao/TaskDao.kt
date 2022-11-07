package com.practica.todoapp.data.datasource.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.practica.todoapp.data.datasource.database.entity.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table WHERE date = :date AND status in (0,1) ORDER BY date, time, status COLLATE NOCASE ASC")
    suspend fun getAll(date:String): List<TaskEntity>

    @Query("SELECT * FROM task_table WHERE id IN (:tastkId)")
    suspend fun getById(tastkId: Int): TaskEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(task: TaskEntity):Long

    @Delete
    suspend fun delete(car: TaskEntity)

    @Query("DELETE FROM task_table")
    suspend fun deleteTable()

    @Update
    suspend fun update(car: TaskEntity): Int

    @Query("SELECT * FROM task_table WHERE name LIKE '%'|| :query || '%' OR description LIKE '%'|| :query || '%' OR type LIKE '%'|| :query || '%' ")
     fun search(query: String): LiveData<List<TaskEntity>>


}