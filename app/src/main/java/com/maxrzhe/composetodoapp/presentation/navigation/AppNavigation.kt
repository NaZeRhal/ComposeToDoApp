package com.maxrzhe.composetodoapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    val router = remember(navController) {
        Router(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = Screens.TasksList.route
    ) {
        taskListComposable(navigateToDetailScreen = router.goToTaskDetail)
        taskDetailComposable(navigateToListScreen = router.goToTasksList)
    }
}