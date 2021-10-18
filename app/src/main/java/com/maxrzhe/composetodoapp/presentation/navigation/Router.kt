package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.navigation.NavController
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailShowEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailTaskNavigation
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailUiEvent

class Router(navController: NavController) {
    val fromDetailToList: (event: DetailUiEvent) -> Unit = { event ->
        when (event) {
            is DetailShowEvent -> { }
            DetailTaskNavigation.AfterAddOrUpdateNavigation -> {
                navController.navigate(Screens.TasksList.route) {
                    popUpTo(Screens.TasksList.route) {
                        inclusive = true
                    }
                }
            }
            is DetailTaskNavigation.DeleteRequest -> {
                navController.navigate(Screens.TasksList.createRoute(event.deleteTaskId)) {
                    popUpTo(Screens.TasksList.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    val fromListToDetail: (Int) -> Unit = { taskId ->
        navController.navigate(Screens.DetailTask.createRoute(taskId)) {
            launchSingleTop = true
        }
    }

    val fromSplashToList: () -> Unit = {
        navController.navigate(Screens.TasksList.route) {
            popUpTo(Screens.Splash.route) {
                inclusive = true
            }
        }
    }
}