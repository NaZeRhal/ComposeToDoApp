package com.maxrzhe.composetodoapp.presentation.tasks_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.presentation.ui.theme.HighPriorityColor
import com.maxrzhe.composetodoapp.presentation.ui.theme.LARGE_PADDING
import com.maxrzhe.composetodoapp.presentation.ui.theme.PRIORITY_INDICATOR_SIZE
import com.maxrzhe.composetodoapp.presentation.ui.theme.TASK_ITEM_ELEVATION

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToDetailScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = { navigateToDetailScreen(toDoTask.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = toDoTask.title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(8f)
                        .alpha(ContentAlpha.high)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                text = toDoTask.description,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TaskItemRedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGE_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(R.string.delete_button),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "New task",
            description = "This is a new task!\nWith several lines\nHow to do that?",
            priority = Priority.HIGH
        ),
        navigateToDetailScreen = {}
    )
}

@Composable
@Preview(showBackground = true)
fun TaskItemRedBackgroundPreview() {
    Column(modifier = Modifier.fillMaxWidth().height(100.dp)) {
        TaskItemRedBackground(degrees = 0f)
    }
}