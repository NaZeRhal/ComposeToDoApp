package com.maxrzhe.composetodoapp.presentation.tasks.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.presentation.ui.theme.fabAddBackgroundColor

@Composable
fun TasksListFab(
    onClick: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onClick(-1) },
        backgroundColor = MaterialTheme.colors.fabAddBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.fab_add_description),
            tint = Color.White
        )
    }
}