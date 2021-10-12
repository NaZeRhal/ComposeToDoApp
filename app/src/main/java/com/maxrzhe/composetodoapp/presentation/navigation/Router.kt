package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.navigation.NavController

class Router(navController: NavController) {
    val goToTasksList: () -> Unit = {
        navController.navigate(Screens.TasksList.route) {
            popUpTo(Screens.TasksList.route) {
                inclusive = true
            }
        }
    }

    val goToTaskDetail: (Int) -> Unit = { taskId ->
        navController.navigate(Screens.DetailTask.createRoute(taskId)) {
            launchSingleTop = true
        }
    }
}