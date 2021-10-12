package com.maxrzhe.composetodoapp.domain.repository

import com.maxrzhe.composetodoapp.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    fun getAllTasks(): Flow<List<ToDoTask>>

    suspend fun getTaskById(id: Int): ToDoTask

    suspend fun addTask(task: ToDoTask)

    suspend fun updateTask(task: ToDoTask)

    suspend fun deleteTask(task: ToDoTask)

    suspend fun deleteAll()

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    fun sortByLowPriority(): Flow<List<ToDoTask>>

    fun sortByHighPriority(): Flow<List<ToDoTask>>
}