package com.maxrzhe.composetodoapp.presentation.detail_screen.events

import com.maxrzhe.composetodoapp.data.models.Priority


sealed class DetailScreenEvent {
    object AddOrUpdateTask : DetailScreenEvent()
    object DeleteTask : DetailScreenEvent()
    object Back : DetailScreenEvent()
    object Close : DetailScreenEvent()
}

sealed class TaskChangeEvent {
    data class EnterTitle(val newTitle: String) : TaskChangeEvent()
    data class EnterDescription(val newDescription: String) : TaskChangeEvent()
    data class ChangePriority(val priority: Priority) : TaskChangeEvent()

}



