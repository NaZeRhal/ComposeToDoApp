package com.maxrzhe.composetodoapp.presentation.tasks_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxrzhe.composetodoapp.presentation.states.CommonScreenState
import com.maxrzhe.composetodoapp.presentation.states.SuccessState
import com.maxrzhe.composetodoapp.presentation.tasks_screen.components.TaskListContent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.components.TasksListFab
import com.maxrzhe.composetodoapp.presentation.tasks_screen.components.appbar.TaskListAppBar
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListScreenEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListUiEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.viewmodel.TasksListViewModel
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun TasksListScreen(
    navigateToDetailScreen: (taskId: Int) -> Unit,
    listViewModel: TasksListViewModel = hiltViewModel()
) {
    val screenState = listViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        listViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is TasksListUiEvent.ShowErrorSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is TasksListUiEvent.ShowDeleteSnackBar -> {
                    val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "UNDO",
                        duration = SnackbarDuration.Short
                    )
                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                        listViewModel.onRestoreTask()
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TaskListAppBar(viewModel = listViewModel)
        },
        floatingActionButton = {
            TasksListFab(onClick = navigateToDetailScreen)
        }
    ) {
        when (screenState) {
            is CommonScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is SuccessState.WithData -> {
                TaskListContent(
                    tasks = screenState.data,
                    onSwipeToDelete = { listViewModel.onEvent(TasksListScreenEvent.Delete(it)) },
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
            else -> Unit
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@Preview
private fun TaskListScreenPreview() {
    TasksListScreen(navigateToDetailScreen = { })
}