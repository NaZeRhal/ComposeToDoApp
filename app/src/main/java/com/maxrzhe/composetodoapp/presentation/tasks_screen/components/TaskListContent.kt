package com.maxrzhe.composetodoapp.presentation.tasks_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.presentation.ui.theme.EMPTY_TASKS_ICON_SIZE
import com.maxrzhe.composetodoapp.presentation.ui.theme.MediumGray

@ExperimentalMaterialApi
@Composable
fun TaskListContent(
    tasks: List<ToDoTask>,
    navigateToDetailScreen: (taskId: Int) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(
                items = tasks,
                key = { task -> task.id }
            ) { task ->
                TaskItem(
                    toDoTask = task,
                    navigateToDetailScreen = navigateToDetailScreen
                )
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