package com.maxrzhe.composetodoapp.presentation.navigation

sealed class Screens(val route: String) {

    object TasksList : Screens("tasks_list")
    object DetailTask : Screens("detail_task/{taskId}") {
        const val TASK_ARG_KEY = "taskId"
        fun createRoute(taskId: Int): String = "detail_task/$taskId"
    }
}
