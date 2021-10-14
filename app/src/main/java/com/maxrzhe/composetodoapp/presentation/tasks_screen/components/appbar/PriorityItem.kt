package com.maxrzhe.composetodoapp.presentation.tasks_screen.components.appbar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.presentation.ui.theme.*

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
            drawCircle(
                color = priority.color
            )
        }
        Spacer(modifier = Modifier.width(MEDIUM_PADDING))
        Text(
            text = priority.name,
            style = Typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0x000000)
fun PriorityItemPreview() {
    ComposeToDoAppTheme(darkTheme = true) {
        PriorityItem(priority = Priority.MEDIUM)
    }
}