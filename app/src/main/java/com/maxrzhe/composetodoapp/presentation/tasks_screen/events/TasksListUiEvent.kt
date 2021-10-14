package com.maxrzhe.composetodoapp.presentation.tasks_screen.events

sealed class TasksListUiEvent {
    data class ShowSnackBar(val message: String) : TasksListUiEvent()
}