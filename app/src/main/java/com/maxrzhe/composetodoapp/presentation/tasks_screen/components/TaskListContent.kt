package com.maxrzhe.composetodoapp.presentation.tasks_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.presentation.ui.theme.EMPTY_TASKS_ICON_SIZE
import com.maxrzhe.composetodoapp.presentation.ui.theme.MediumGray
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun TaskListContent(
    tasks: List<ToDoTask>,
    navigateToDetailScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                items = tasks,
                key = { task -> task.id }
            ) { task ->
                val dismissState = rememberDismissState()
                val dismissDirection = dismissState.dismissDirection
                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

                if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                    val scope = rememberCoroutineScope()
                    scope.launch {
                        delay(300)
                        onSwipeToDelete(task.id)
                    }

                }

                val degrees by animateFloatAsState(
                    targetValue =
                    if (dismissState.targetValue == DismissValue.Default)
                        0f
                    else
                        -45f
                )

                var itemAppeared by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = true) {
                    itemAppeared = true
                }

                AnimatedVisibility(
                    visible = itemAppeared && !isDismissed,
                    enter = expandVertically(
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                        background = { TaskItemRedBackground(degrees = degrees) }
                    ) {
                        TaskItem(
                            toDoTask = task,
                            navigateToDetailScreen = navigateToDetailScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SentimentVeryDissatisfied,
            contentDescription = stringResource(R.string.empty_list_icon),
            Modifier.size(EMPTY_TASKS_ICON_SIZE),
            tint = MediumGray
        )
        Text(
            text = stringResource(R.string.no_tasks_found),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h5.fontSize
        )
    }
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent()
}