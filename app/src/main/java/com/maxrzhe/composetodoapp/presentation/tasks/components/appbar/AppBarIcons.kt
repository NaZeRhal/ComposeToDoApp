package com.maxrzhe.composetodoapp.presentation.tasks.components.appbar

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.presentation.tasks.components.PriorityItem

@Composable
fun SearchIconButton(onSearchIconClick: () -> Unit) {
    IconButton(onClick = { onSearchIconClick() }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(
                id = R.string.appbar_search_icon_description
            )
        )
    }
}

@Composable
fun SortIconButton(onSortIconClick: (Priority) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = stringResource(
                id = R.string.appbar_sort_icon_description
            )
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        for (priority in Priority.values()) {
            DropdownMenuItem(onClick = {
                expanded = false
            }) {
                PriorityItem(priority = priority)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchIconButtonPreview() {
    SearchIconButton(onSearchIconClick = {})
}

@Composable
@Preview(showBackground = true)
fun SortIconButtonPreview() {
    SortIconButton(onSortIconClick = {})
}