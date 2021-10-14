package com.maxrzhe.composetodoapp.presentation.tasks_screen.events

import com.maxrzhe.composetodoapp.data.models.Priority

sealed class TaskListEvent {
    object OpenSearchBar: TaskListEvent()
    object CloseSearchBar: TaskListEvent()
    object ClearSearchBar: TaskListEvent()
    data class ChangeSearchText(val newText: String): TaskListEvent()
    data class Search(val text: String): TaskListEvent()
    data class Sort(val priority: Priority) : TaskListEvent()
    object DeleteAll: TaskListEvent()
}