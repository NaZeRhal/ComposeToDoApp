package com.maxrzhe.composetodoapp.presentation.detail_screen.components.appbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.core.util.AppBarDetailClickEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.AppBarDetailEvent

@Composable
fun TaskDetailAppBar(
    isNewTask: Boolean,
    taskTitle: String,
    onAppBarButtonClick: AppBarDetailClickEvent
) {

    if (!isNewTask) {
        UpdateTaskAppBar(
            taskTitle = taskTitle,
            onAppBarButtonClick = onAppBarButtonClick
        )
    } else {
        NewTaskAppBar(
            onAppBarButtonClick = onAppBarButtonClick
        )
    }
}

@Composable
fun NewTaskAppBar(
    onAppBarButtonClick: AppBarDetailClickEvent
) {
    TopAppBar(
        navigationIcon = {
            BackButton(onBackClick = onAppBarButtonClick)
        },
        title = {
            Text(
                text = stringResource(R.string.add_new_task_title),
                color = MaterialTheme.colors.onPrimary
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            AddButton(onAddClick = onAppBarButtonClick)
        }
    )
}

@Composable
fun UpdateTaskAppBar(
    taskTitle: String,
    onAppBarButtonClick: AppBarDetailClickEvent

) {
    TopAppBar(
        navigationIcon = {
            CloseButton(onCloseClick = onAppBarButtonClick)
        },
        title = {
            Text(
                text = taskTitle,
                color = MaterialTheme.colors.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            DeleteButton(onDeleteClick = onAppBarButtonClick)
            SaveUpdatesButton(onUpdateClick = onAppBarButtonClick)
        }
    )
}

@Composable
fun BackButton(
    onBackClick: AppBarDetailClickEvent
) {
    IconButton(onClick = { onBackClick(AppBarDetailEvent.Back) }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(
                R.string.back_button
            ),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun AddButton(
    onAddClick: AppBarDetailClickEvent
) {
    IconButton(onClick = { onAddClick(AppBarDetailEvent.AddOrUpdateTask) }) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(
                R.string.add_button
            ),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun CloseButton(
    onCloseClick: AppBarDetailClickEvent
) {
    IconButton(onClick = { onCloseClick(AppBarDetailEvent.Close) }) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(
                R.string.close_button
            ),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun SaveUpdatesButton(
    onUpdateClick: AppBarDetailClickEvent
) {
    IconButton(onClick = { onUpdateClick(AppBarDetailEvent.AddOrUpdateTask) }) {
        Icon(
            imageVector = Icons.Default.Save,
            contentDescription = stringResource(
                R.string.update_button
            ),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun DeleteButton(
    onDeleteClick: AppBarDetailClickEvent
) {
    IconButton(onClick = { onDeleteClick(AppBarDetailEvent.DeleteTask) }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(
                R.string.delete_button
            ),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar(
        onAppBarButtonClick = {}
    )
}

@Composable
@Preview
fun UpdateTaskAppBarPreview() {
    UpdateTaskAppBar(
        taskTitle = "Selected task from Max",
        onAppBarButtonClick = {}
    )
}