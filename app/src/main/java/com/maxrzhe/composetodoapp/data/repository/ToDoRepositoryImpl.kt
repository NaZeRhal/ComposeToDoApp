package com.maxrzhe.composetodoapp.data.repository

import com.maxrzhe.composetodoapp.data.local.ToDoDao
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.domain.repository.ToDoRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepositoryImpl @Inject constructor(private val toDoDao: ToDoDao) : ToDoRepository {
    override fun getAllTasks(): Flow<List<ToDoTask>> {
        return toDoDao.getAllTasks()
    }

    override fun getTaskById(id: Int): Flow<ToDoTask> {
        return toDoDao.getTaskById(id)
    }

    override suspend fun addTask(task: ToDoTask) {
        toDoDao.addTask(task)
    }

    override suspend fun updateTask(task: ToDoTask) {
        toDoDao.updateTask(task)
    }

    override suspend fun deleteTaskById(id: Int) {
        toDoDao.deleteTaskById(id)
    }

    override suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    override fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery)
    }

    override fun sortByLowPriority(): Flow<List<ToDoTask>> {
        return toDoDao.sortByLowPriority()
    }

    override fun sortByHighPriority(): Flow<List<ToDoTask>> {
        return toDoDao.sortByHighPriority()
    }
}