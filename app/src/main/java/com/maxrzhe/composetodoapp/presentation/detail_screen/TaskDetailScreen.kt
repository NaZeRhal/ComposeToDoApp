package com.maxrzhe.composetodoapp.presentation.detail_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxrzhe.composetodoapp.presentation.detail_screen.components.DetailTaskContent
import com.maxrzhe.composetodoapp.presentation.detail_screen.components.appbar.TaskDetailAppBar
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailShowEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailTaskNavigation
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailUiEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.viewmodel.DetailTaskViewModel
import com.maxrzhe.composetodoapp.presentation.states.CommonScreenState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TaskDetailScreen(
    navigateToListScreen: (event: DetailUiEvent) -> Unit,
    detailTaskViewModel: DetailTaskViewModel = hiltViewModel()
) {

    val screenState = detailTaskViewModel.screenState.value
    val title = detailTaskViewModel.titleState.value
    val description = detailTaskViewModel.descriptionState.value
    val priority = detailTaskViewModel.priorityState.value
    val isNewTask = detailTaskViewModel.isNewTask.value

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        detailTaskViewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is DetailTaskNavigation -> navigateToListScreen(event)
                is DetailShowEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is DetailShowEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TaskDetailAppBar(
                isNewTask = isNewTask,
                taskTitle = title,
                onAppBarButtonClick = { event -> detailTaskViewModel.onAppBarEvent(event) }
            )
        }) {
        when (screenState) {
            is CommonScreenState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CommonScreenState.Error -> {

            }
            else -> {
                DetailTaskContent(
                    title = title,
                    description = description,
                    priority = priority,
                    onTaskChangeClickEvent = { event ->
                        detailTaskViewModel.onTaskChangeEvent(event)
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun TaskDetailScreenPreview() {
    TaskDetailScreen(navigateToListScreen = {})
}