package com.maxrzhe.composetodoapp.presentation.navigation

sealed class Screens(val route: String) {

    object TasksList : Screens("tasks_list?deleteTaskId={deleteTaskId}") {
        const val DELETE_TASK_KEY = "deleteTaskId"
        fun createRoute(deleteTaskId: Int): String = "tasks_list?$DELETE_TASK_KEY=$deleteTaskId"
    }
    object DetailTask : Screens("detail_task/{taskId}") {
        const val TASK_ARG_KEY = "taskId"
        fun createRoute(taskId: Int): String = "detail_task/$taskId"
    }
}
