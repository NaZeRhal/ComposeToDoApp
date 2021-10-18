package com.maxrzhe.composetodoapp.presentation.detail_screen.components.appbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.core.util.AppBarDetailClickEvent
import com.maxrzhe.composetodoapp.presentation.components.DisplayAlertDialog
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailScreenEvent

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
            UpdateAppBarActions(
                title = taskTitle,
                onAppBarButtonClick = onAppBarButtonClick
            )
        }
    )
}

@Composable
fun UpdateAppBarActions(
    title: String,
    onAppBarButtonClick: AppBarDetailClickEvent
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(R.string.delete_task_alert_title, title),
        message = stringResource(R.string.delete_task_message, title),
        openDialog = openDialog,
        onDismiss = {
            openDialog = false
        },
        onConfirm = {
            openDialog = false
            onAppBarButtonClick(DetailScreenEvent.DeleteTask)
        }
    )

    DeleteButton(onDeleteClick = { openDialog = true })
    SaveUpdatesButton(onUpdateClick = onAppBarButtonClick)

}

@Composable
fun BackButton(
    onBackClick: AppBarDetailClickEvent
) {
    IconButton(onClick = { onBackClick(DetailScreenEvent.Back) }) {
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
    IconButton(onClick = { onAddClick(DetailScreenEvent.AddOrUpdateTask) }) {
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
    IconButton(onClick = { onCloseClick(DetailScreenEvent.Close) }) {
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
    IconButton(onClick = { onUpdateClick(DetailScreenEvent.AddOrUpdateTask) }) {
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
    onDeleteClick: () -> Unit
) {
    IconButton(onClick = { onDeleteClick() }) {
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