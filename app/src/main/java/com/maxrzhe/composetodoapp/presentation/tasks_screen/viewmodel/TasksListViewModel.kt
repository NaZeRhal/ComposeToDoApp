package com.maxrzhe.composetodoapp.presentation.tasks_screen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.domain.repository.ToDoRepository
import com.maxrzhe.composetodoapp.presentation.TAG
import com.maxrzhe.composetodoapp.presentation.states.CommonScreenState
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TaskListEvent
import com.maxrzhe.composetodoapp.presentation.states.ScreenState
import com.maxrzhe.composetodoapp.presentation.states.SuccessState
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListUiEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.states.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _searchAppBarState = mutableStateOf<SearchAppBarState>(SearchAppBarState.Closed)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _screenState = mutableStateOf<ScreenState<List<ToDoTask>>>(CommonScreenState.Idle)
    val screenState: State<ScreenState<List<ToDoTask>>> = _screenState

    private val _uiEventFlow = MutableSharedFlow<TasksListUiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        getAllTasks()
    }

    fun onAppBarEvent(taskListEvent: TaskListEvent) {
        when (taskListEvent) {
            is TaskListEvent.CloseSearchBar -> {
                _searchAppBarState.value = SearchAppBarState.Closed
            }
            is TaskListEvent.OpenSearchBar -> {
                _searchAppBarState.value = SearchAppBarState.Opened
            }
            is TaskListEvent.ChangeSearchText -> {
                _searchText.value = taskListEvent.newText
            }
            is TaskListEvent.ClearSearchBar -> {
                _searchText.value = ""
                getAllTasks()
            }
            is TaskListEvent.Search -> {
                _screenState.value = CommonScreenState.Loading
                viewModelScope.launch {
                    try {
                        repository.searchDatabase(taskListEvent.text).collect {
                            _screenState.value = SuccessState.WithData(it)
                        }
                    } catch (e: Exception) {
                        _uiEventFlow.emit(
                            TasksListUiEvent.ShowSnackBar(
                                e.localizedMessage ?: "Unexpected error"
                            )
                        )
                    }
                }
            }
            is TaskListEvent.DeleteAll -> {
                viewModelScope.launch {
                    repository.deleteAll()
                }
            }
            is TaskListEvent.Sort -> {

            }
        }
    }

    private fun getAllTasks() {
        viewModelScope.launch {
            _screenState.value = CommonScreenState.Loading
            delay(400)
            try {
                repository.getAllTasks().collect {
                    _screenState.value = SuccessState.WithData(it)
                }
            } catch (e: Exception) {
                _uiEventFlow.emit(
                    TasksListUiEvent.ShowSnackBar(
                        e.localizedMessage ?: "Unexpected error"
                    )
                )
            }
        }
    }
}