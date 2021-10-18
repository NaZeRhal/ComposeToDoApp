package com.maxrzhe.composetodoapp.presentation.tasks_screen.events

import com.maxrzhe.composetodoapp.data.models.Priority

sealed class TasksListScreenEvent {
    object OpenSearchBar: TasksListScreenEvent()
    object CloseSearchBar: TasksListScreenEvent()
    object ClearSearchBar: TasksListScreenEvent()
    data class ChangeSearchText(val newText: String): TasksListScreenEvent()
    data class Search(val text: String): TasksListScreenEvent()
    data class Sort(val priority: Priority) : TasksListScreenEvent()
    data class Delete(val taskId: Int): TasksListScreenEvent()
    object DeleteAll: TasksListScreenEvent()
}