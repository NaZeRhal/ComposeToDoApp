package com.maxrzhe.composetodoapp.presentation.tasks

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.presentation.tasks.components.TaskListAppBar
import com.maxrzhe.composetodoapp.presentation.tasks.components.TasksListFab

@Composable
fun TasksListScreen(
    navigateToDetailScreen: (taskId: Int) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TaskListAppBar() },
        floatingActionButton = {
            TasksListFab(onClick = navigateToDetailScreen)
        },
        content = {

        }
    )
}

@Composable
@Preview
fun TaskListScreenPreview() {
    TasksListScreen(navigateToDetailScreen = { })
}