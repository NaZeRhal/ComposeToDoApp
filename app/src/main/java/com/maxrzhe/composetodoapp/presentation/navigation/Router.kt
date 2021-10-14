package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.navigation.NavController
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailShowEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailTaskNavigation
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailUiEvent

class Router(navController: NavController) {
    val goToTasksList: (event: DetailUiEvent) -> Unit = { event ->
        when (event) {
            is DetailShowEvent.ShowSnackBar -> { }
            DetailTaskNavigation.AfterAddOrUpdateNavigation -> {
                navController.navigate(Screens.TasksList.route) {
                    popUpTo(Screens.TasksList.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val goToTaskDetail: (Int) -> Unit = { taskId ->
        navController.navigate(Screens.DetailTask.createRoute(taskId)) {
            launchSingleTop = true
        }
    }
}