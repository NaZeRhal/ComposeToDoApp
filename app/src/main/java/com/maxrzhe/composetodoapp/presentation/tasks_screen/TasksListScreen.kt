package com.maxrzhe.composetodoapp.presentation.tasks_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxrzhe.composetodoapp.presentation.TAG
import com.maxrzhe.composetodoapp.presentation.states.CommonScreenState
import com.maxrzhe.composetodoapp.presentation.states.SuccessState
import com.maxrzhe.composetodoapp.presentation.tasks_screen.components.TaskListContent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.components.TasksListFab
import com.maxrzhe.composetodoapp.presentation.tasks_screen.components.appbar.TaskListAppBar
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListUiEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.viewmodel.TasksListViewModel
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun TasksListScreen(
    navigateToDetailScreen: (taskId: Int) -> Unit,
    viewModel: TasksListViewModel = hiltViewModel()
) {
    val screenState = viewModel.screenState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collectLatest { event ->
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
                        duration = SnackbarDuration.Long
                    )
                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                        viewModel.onRestoreTask()
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TaskListAppBar(viewModel = viewModel)
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
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
            else -> Unit
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
private fun TaskListScreenPreview() {
    TasksListScreen(navigateToDetailScreen = { })
}