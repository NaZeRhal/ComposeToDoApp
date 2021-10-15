package com.maxrzhe.composetodoapp.presentation.tasks_screen.components.appbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.presentation.ui.theme.MEDIUM_PADDING

@Composable
fun SearchIconButton(onSearchIconClick: () -> Unit) {
    IconButton(onClick = { onSearchIconClick() }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(
                id = R.string.appbar_search_icon_description
            ),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun SortIconButton(onSortIconClick: (Priority) -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = stringResource(R.string.appbar_sort_icon_description),
            tint = MaterialTheme.colors.onPrimary
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        for (priority in Priority.values()) {
            if (priority != Priority.MEDIUM) {
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onSortIconClick(priority)
                    }
                ) {
                    PriorityItem(priority = priority)
                }
            }
        }
    }
}

@Composable
fun DeleteAllIconButton(onDeleteAllClick: () -> Unit) {

    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(R.string.appbar_delete_all_icon_description),
            tint = MaterialTheme.colors.onPrimary
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            onClick = {
                expanded = false
                onDeleteAllClick()
            }
        ) {
            Text(
                text = stringResource(R.string.appbar_delete_all_icon_description),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = MEDIUM_PADDING)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF6200EE)
fun SearchIconButtonPreview() {
    SearchIconButton(onSearchIconClick = {})
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF6200EE)
fun SortIconButtonPreview() {
    SortIconButton(onSortIconClick = {})
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF6200EE)
fun DeleteAllIconButtonPreview() {
    DeleteAllIconButton(onDeleteAllClick = {})
}