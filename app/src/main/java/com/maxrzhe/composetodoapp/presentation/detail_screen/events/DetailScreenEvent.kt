package com.maxrzhe.composetodoapp.presentation.detail_screen.events

import com.maxrzhe.composetodoapp.data.models.Priority


sealed class AppBarDetailEvent {
    object AddOrUpdateTask : AppBarDetailEvent()
    object DeleteTask : AppBarDetailEvent()
    object Back : AppBarDetailEvent()
    object Close : AppBarDetailEvent()
}

sealed class TaskChangeEvent {
    data class EnterTitle(val newTitle: String) : TaskChangeEvent()
    data class EnterDescription(val newDescription: String) : TaskChangeEvent()
    data class ChangePriority(val priority: Priority) : TaskChangeEvent()

}



