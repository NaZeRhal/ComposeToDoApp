package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maxrzhe.composetodoapp.presentation.tasks.TasksListScreen

fun NavGraphBuilder.taskListComposable(
    navigateToDetailScreen: (taskId: Int) -> Unit
) {
    composable(Screens.TasksList.route) {
        TasksListScreen(navigateToDetailScreen = navigateToDetailScreen)
    }
}

fun NavGraphBuilder.taskDetailComposable() {
    composable(
        route = Screens.DetailTask.route,
        arguments = listOf(navArgument(Screens.DetailTask.TASK_ID) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->

    }
}


