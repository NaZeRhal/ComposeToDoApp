package com.maxrzhe.composetodoapp.presentation.tasks.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.presentation.tasks.components.appbar.SearchIconButton
import com.maxrzhe.composetodoapp.presentation.tasks.components.appbar.SortIconButton
import com.maxrzhe.composetodoapp.presentation.ui.theme.topAppBarBackgroundColor
import com.maxrzhe.composetodoapp.presentation.ui.theme.topAppBarContentColor

@Composable
fun TaskListAppBar() {
    DefaultAppBar(
        onSearchClick = {},
        onSortClick = {}
    )
}

@Composable
fun DefaultAppBar(
    onSearchClick: () -> Unit,
    onSortClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            SearchIconButton(onSearchIconClick = onSearchClick)
            SortIconButton(onSortIconClick = { })
        }
    )
}

@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(onSearchClick = {}, onSortClick = {})
}