package com.maxrzhe.composetodoapp.presentation.tasks_screen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.data.repository.DataStoreRepository
import com.maxrzhe.composetodoapp.domain.repository.ToDoRepository
import com.maxrzhe.composetodoapp.presentation.TAG
import com.maxrzhe.composetodoapp.presentation.navigation.Screens
import com.maxrzhe.composetodoapp.presentation.states.CommonScreenState
import com.maxrzhe.composetodoapp.presentation.states.ScreenState
import com.maxrzhe.composetodoapp.presentation.states.SuccessState
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListScreenEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.events.TasksListUiEvent
import com.maxrzhe.composetodoapp.presentation.tasks_screen.states.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var job: Job? = null

    private var lastDeletedTask: ToDoTask? = null

    private val _searchAppBarState = mutableStateOf<SearchAppBarState>(SearchAppBarState.Closed)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchText = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _screenState = mutableStateOf<ScreenState<List<ToDoTask>>>(CommonScreenState.Idle)
    val screenState: State<ScreenState<List<ToDoTask>>> = _screenState

    private val _uiEventFlow = MutableSharedFlow<TasksListUiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>(Screens.TasksList.DELETE_TASK_KEY)?.let { id ->
            if (id > -1) {
                deleteTask(id)
            }
        }
        retrieveTasksByStoreState()
    }

    fun onEvent(event: TasksListScreenEvent) {
        Log.i(TAG, "onAppBarEvent: $event")
        when (event) {
            is TasksListScreenEvent.CloseSearchBar -> {
                _searchAppBarState.value = SearchAppBarState.Closed
            }
            is TasksListScreenEvent.OpenSearchBar -> {
                _searchAppBarState.value = SearchAppBarState.Opened
            }
            is TasksListScreenEvent.ChangeSearchText -> {
                _searchText.value = event.newText
            }
            is TasksListScreenEvent.ClearSearchBar -> {
                _searchText.value = ""
                getAllTasks()
                _searchAppBarState.value = SearchAppBarState.Opened
            }
            is TasksListScreenEvent.Search -> {
                _screenState.value = CommonScreenState.Loading
                viewModelScope.launch {
                    try {
                        repository.searchDatabase("%${event.text}%").collect {
                            Log.i(TAG, "onAppBarEvent: search$it")
                            _screenState.value = SuccessState.WithData(it)
                        }
                    } catch (e: Exception) {
                        _uiEventFlow.emit(
                            TasksListUiEvent.ShowErrorSnackBar(
                                e.localizedMessage ?: "Unexpected error"
                            )
                        )
                    }
                }
                _searchAppBarState.value = SearchAppBarState.Triggered
            }
            is TasksListScreenEvent.DeleteAll -> {
                viewModelScope.launch(IO) {
                    repository.deleteAll()
                }
            }
            is TasksListScreenEvent.Sort -> {
                val priority = event.priority
                viewModelScope.launch(IO) {
                    dataStoreRepository.persistSortState(priority)
                    getAllTasks(priority)
                }
            }
            is TasksListScreenEvent.Delete -> {
                deleteTask(event.taskId)
            }
        }
    }

    fun onRestoreTask() {
        viewModelScope.launch(IO) {
            lastDeletedTask?.let {
                Log.i(TAG, "onRestoreTask: $it")
                repository.addTask(it.copy(id = 0))
            }
        }
    }

    private fun retrieveTasksByStoreState() {
        viewModelScope.launch(IO) {
            dataStoreRepository.readSortState.firstOrNull()?.let { priorityName ->
                getAllTasks(Priority.valueOf(priorityName))
            }
        }
    }

    private fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            val task: ToDoTask? = repository.getTaskById(taskId).firstOrNull()
            task?.let {
                Log.i(TAG, "deleteTask: task for delete = $it")
                lastDeletedTask = it
                withContext(IO) {
                    repository.deleteTaskById(taskId)
                }
                delay(500)
                _uiEventFlow.emit(TasksListUiEvent.ShowDeleteSnackBar("Task deleted"))
            }
        }
    }

    private fun getAllTasks(priority: Priority = Priority.NONE) {
        job?.cancel()
        job = viewModelScope.launch {
            _screenState.value = CommonScreenState.Loading
            delay(400)
            try {
                when (priority) {
                    Priority.LOW -> {
                        repository.sortByLowPriority().collect {
                            _screenState.value = SuccessState.WithData(it)
                        }
                    }
                    Priority.HIGH -> {
                        repository.sortByHighPriority().collect {
                            _screenState.value = SuccessState.WithData(it)
                        }
                    }
                    else -> {
                        repository.getAllTasks().collect {
                            _screenState.value = SuccessState.WithData(it)
                        }
                    }
                }
            } catch (e: Exception) {
                _uiEventFlow.emit(
                    TasksListUiEvent.ShowDeleteSnackBar(
                        e.localizedMessage ?: "Unexpected error"
                    )
                )
            }
        }
    }
}