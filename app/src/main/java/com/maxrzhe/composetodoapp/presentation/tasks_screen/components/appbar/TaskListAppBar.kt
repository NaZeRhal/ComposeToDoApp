package com.maxrzhe.composetodoapp.presentation.tasks_screen.components.appbar

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.maxrzhe.composetodoapp.R
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.presentation.TAG
import com.maxrzhe.composetodoapp.presentation.components.DisplayAlertDialog
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListScreenEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.states.SearchAppBarState
import com.maxrzhe.composetodoapp.presentation.tasks_screen.viewmodel.TasksListViewModel
import com.maxrzhe.composetodoapp.presentation.ui.theme.SEARCH_TOP_BAR_HEIGHT
import com.maxrzhe.composetodoapp.presentation.ui.theme.topAppBarContentColor

@Composable
fun TaskListAppBar(
    viewModel: TasksListViewModel
) {
    val text = viewModel.searchText.value

    when (viewModel.searchAppBarState.value) {
        is SearchAppBarState.Closed -> {
            DefaultAppBar(
                onSearchClick = { viewModel.onEvent(TasksListScreenEvent.OpenSearchBar) },
                onSortClick = { priority ->
                    viewModel.onEvent(TasksListScreenEvent.Sort(priority))
                },
                onDeleteAllClick = {
                    viewModel.onEvent(TasksListScreenEvent.DeleteAll)
                }
            )
        }
        else -> {
            SearchAppBar(
                text = text,
                onValueChange = { newText ->
                    viewModel.onEvent(TasksListScreenEvent.ChangeSearchText(newText))
                },
                onSearchClick = { searchText ->
                    if (text.isNotBlank()) {
                        viewModel.onEvent(TasksListScreenEvent.Search(searchText))
                    }
                },
                onCloseClick = {
                    if (text.isBlank() && viewModel.searchAppBarState.value !is SearchAppBarState.Triggered) {
                        viewModel.onEvent(TasksListScreenEvent.CloseSearchBar)
                    } else {
                        viewModel.onEvent(TasksListScreenEvent.ClearSearchBar)
                    }
                }
            )
        }
    }
}

@Composable
fun DefaultAppBar(
    onSearchClick: () -> Unit,
    onSortClick: (Priority) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tasks_title),
                color = MaterialTheme.colors.onPrimary
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            DefaultAppBarActions(
                onSearchClick = onSearchClick,
                onSortClick = onSortClick,
                onDeleteAllClick = onDeleteAllClick
            )
        }
    )
}

@Composable
fun DefaultAppBarActions(
    onSearchClick: () -> Unit,
    onSortClick: (Priority) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(R.string.remove_all),
        message = stringResource(R.string.delete_all_message),
        openDialog = openDialog,
        onDismiss = {
            openDialog = false
        },
        onConfirm = {
            openDialog = false
            onDeleteAllClick()
        }
    )

    SearchIconButton(onSearchIconClick = onSearchClick)
    SortIconButton(onSortIconClick = onSortClick)
    DeleteAllIconButton(onDeleteAllClick = { openDialog = true })
}

@Composable
fun SearchAppBar(
    text: String,
    onValueChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onCloseClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(SEARCH_TOP_BAR_HEIGHT),
        color = MaterialTheme.colors.primary,
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            leadingIcon = {
                IconButton(
                    onClick = {
                        onSearchClick(text.trim())
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.alpha(ContentAlpha.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.appbar_search_icon_description),
                        tint = MaterialTheme.colors.topAppBarContentColor,
                        modifier = Modifier.clickable { onSearchClick(text) }
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        onCloseClick()
                    }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.appbar_close_icon_description),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_placeholder),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White,
                    modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(text.trim())
                    focusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(
        onSearchClick = {},
        onSortClick = {},
        onDeleteAllClick = {}
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onValueChange = {},
        onSearchClick = {},
        onCloseClick = {}
    )
}