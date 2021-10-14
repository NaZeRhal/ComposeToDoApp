package com.maxrzhe.composetodoapp.presentation.navigation

import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maxrzhe.composetodoapp.presentation.TAG
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.AppBarDetailEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.TaskDetailScreen
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailUiEvent
import com.maxrzhe.composetodoapp.presentation.navigation.Screens.DetailTask.TASK_ARG_KEY
import com.maxrzhe.composetodoapp.presentation.tasks_screen.TasksListScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.taskListComposable(
    navigateToDetailScreen: (taskId: Int) -> Unit
) {
    composable(Screens.TasksList.route) {
        TasksListScreen(navigateToDetailScreen = navigateToDetailScreen)
    }
}

fun NavGraphBuilder.taskDetailComposable(
    navigateToListScreen: (event: DetailUiEvent) -> Unit
) {
    composable(
        route = Screens.DetailTask.route,
        arguments = listOf(navArgument(TASK_ARG_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments?.getInt(TASK_ARG_KEY)
        Log.i(TAG, "taskDetailComposable: $taskId")
        TaskDetailScreen(navigateToListScreen = navigateToListScreen)
    }
}


