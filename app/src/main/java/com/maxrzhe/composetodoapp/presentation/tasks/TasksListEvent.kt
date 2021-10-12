package com.maxrzhe.composetodoapp.presentation.tasks

sealed class TasksListEvent {
    object Add: TasksListEvent()
    object Update: TasksListEvent()
    object Delete: TasksListEvent()
    object DeleteAll: TasksListEvent()
    object UnDo: TasksListEvent()
    object NoAction: TasksListEvent()
}
