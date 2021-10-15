package com.maxrzhe.composetodoapp.presentation.tasks_screen.events

sealed class TasksListUiEvent {
    data class ShowErrorSnackBar(val message: String) : TasksListUiEvent()
    data class ShowDeleteSnackBar(val message: String) : TasksListUiEvent()
}