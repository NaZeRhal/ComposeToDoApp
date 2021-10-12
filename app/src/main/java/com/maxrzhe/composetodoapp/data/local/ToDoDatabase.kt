package com.maxrzhe.composetodoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxrzhe.composetodoapp.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {

    abstract val toDoDao: ToDoDao
}