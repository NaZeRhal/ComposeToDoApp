package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.maxrzhe.composetodoapp.presentation.detail_screen.TaskDetailScreen
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailUiEvent
import com.maxrzhe.composetodoapp.presentation.navigation.Screens.DetailTask.TASK_ARG_KEY
import com.maxrzhe.composetodoapp.presentation.navigation.Screens.TasksList.DELETE_TASK_KEY
import com.maxrzhe.composetodoapp.presentation.splash_screen.SplashScreen
import com.maxrzhe.composetodoapp.presentation.tasks_screen.TasksListScreen

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.taskListComposable(
    navigateToDetailScreen: (taskId: Int) -> Unit
) {
    composable(
        route = Screens.TasksList.route,
        arguments = listOf(
            navArgument(
                name = DELETE_TASK_KEY
            ) {
                type = NavType.IntType
                defaultValue = -1
            })
    ) {
        TasksListScreen(navigateToDetailScreen = navigateToDetailScreen)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.taskDetailComposable(
    navigateToListScreen: (event: DetailUiEvent) -> Unit
) {
    composable(
        route = Screens.DetailTask.route,
        enterTransition = { initial, target ->
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 500)
            )
        },
        arguments = listOf(navArgument(TASK_ARG_KEY) {
            type = NavType.IntType
        })
    ) {
        TaskDetailScreen(navigateToListScreen = navigateToListScreen)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.splashScreenComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Screens.Splash.route,
        exitTransition = { _, _ ->
            slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 500)
            )
        }
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}


