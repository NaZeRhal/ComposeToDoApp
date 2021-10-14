package com.maxrzhe.composetodoapp.presentation.tasks_screen.states

sealed class SearchAppBarState {
    object Opened: SearchAppBarState()
    object Closed: SearchAppBarState()
    object Triggered: SearchAppBarState()
}
