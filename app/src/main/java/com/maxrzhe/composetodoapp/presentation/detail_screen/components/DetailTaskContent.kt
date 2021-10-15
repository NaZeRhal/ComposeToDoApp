package com.maxrzhe.composetodoapp.presentation.detail_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.core.util.TaskChangeClickEvent
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.TaskChangeEvent
import com.maxrzhe.composetodoapp.presentation.ui.theme.LARGE_PADDING
import com.maxrzhe.composetodoapp.presentation.ui.theme.SMALL_PADDING

@Composable
fun DetailTaskContent(
    title: String,
    description: String,
    priority: Priority,
    onTaskChangeClickEvent: TaskChangeClickEvent
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { text ->
                onTaskChangeClickEvent(TaskChangeEvent.EnterTitle(text))
            },
            label = {
                Text(text = stringResource(R.string.title_text_field))
            },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = { newPriority ->
                onTaskChangeClickEvent(TaskChangeEvent.ChangePriority(newPriority))
            }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { text ->
                onTaskChangeClickEvent(TaskChangeEvent.EnterDescription(text))
            },
            label = {
                Text(text = stringResource(R.string.description_text_field))
            },
            textStyle = MaterialTheme.typography.body1
        )
    }
}