package com.maxrzhe.composetodoapp.presentation.detail_screen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrzhe.composetodoapp.core.util.Constants.MAX_TITLE_LENGTH
import com.maxrzhe.composetodoapp.data.models.InvalidTaskException
import com.maxrzhe.composetodoapp.data.models.Priority
import com.maxrzhe.composetodoapp.data.models.ToDoTask
import com.maxrzhe.composetodoapp.domain.repository.ToDoRepository
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.*
import com.maxrzhe.composetodoapp.presentation.navigation.Screens
import com.maxrzhe.composetodoapp.presentation.states.CommonScreenState
import com.maxrzhe.composetodoapp.presentation.states.ScreenState
import com.maxrzhe.composetodoapp.presentation.states.SuccessState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailTaskViewModel @Inject constructor(
    private val repository: ToDoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentTaskId: Int? = null

    private val _isNewTask = mutableStateOf(true)
    val isNewTask: State<Boolean> = _isNewTask

    private val _titleState = mutableStateOf("")
    val titleState: State<String> = _titleState

    private val _descriptionState = mutableStateOf("")
    val descriptionState: State<String> = _descriptionState

    private val _priorityState = mutableStateOf(Priority.NONE)
    val priorityState: State<Priority> = _priorityState

    private val _screenState = mutableStateOf<ScreenState<List<ToDoTask>>>(CommonScreenState.Idle)
    val screenState: State<ScreenState<List<ToDoTask>>> = _screenState

    private val _uiEventFlow = MutableSharedFlow<DetailUiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>(Screens.DetailTask.TASK_ARG_KEY)?.let { id ->
            if (id != -1) {
                _isNewTask.value = false

                repository.getTaskById(id).onEach { task ->
                    _screenState.value = CommonScreenState.Loading
                    delay(400)
                    currentTaskId = task.id
                    _titleState.value = task.title
                    _descriptionState.value = task.description
                    _priorityState.value = task.priority
                    _screenState.value = SuccessState.Completed
                }.launchIn(viewModelScope)

            } else {
                _isNewTask.value = true
            }
        }
    }

    fun onAppBarEvent(event: AppBarDetailEvent) {
        when (event) {
            is AppBarDetailEvent.AddOrUpdateTask -> {
                val task = collectTaskFields()
                addOrUpdateTask(task)
            }
            is AppBarDetailEvent.DeleteTask -> {
                currentTaskId?.let { id ->
                    viewModelScope.launch {
                        val task: ToDoTask? = repository.getTaskById(id).firstOrNull()
                        task?.let {
                            withContext(Dispatchers.IO) {
                                repository.deleteTaskById(it.id)
                            }
                            _uiEventFlow.emit(DetailTaskNavigation.AfterAddOrUpdateNavigation)
                        }
                    }
                }
            }
            AppBarDetailEvent.Back -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(DetailTaskNavigation.AfterAddOrUpdateNavigation)
                }
            }
            AppBarDetailEvent.Close -> {
                viewModelScope.launch {
                    _uiEventFlow.emit(DetailTaskNavigation.AfterAddOrUpdateNavigation)
                }
            }
        }
    }

    fun onTaskChangeEvent(event: TaskChangeEvent) {
        when (event) {
            is TaskChangeEvent.ChangePriority -> {
                _priorityState.value = event.priority
            }
            is TaskChangeEvent.EnterDescription -> {
                _descriptionState.value = event.newDescription
            }
            is TaskChangeEvent.EnterTitle -> {
                if (event.newTitle.length < MAX_TITLE_LENGTH) {
                    _titleState.value = event.newTitle
                } else {
                    viewModelScope.launch {
                        _uiEventFlow.emit(DetailShowEvent.ShowToast("Title max length is 20 symbols"))
                    }
                }

            }
        }
    }

    private fun collectTaskFields(): ToDoTask =
        ToDoTask(
            id = currentTaskId ?: -1,
            title = titleState.value,
            description = descriptionState.value,
            priority = priorityState.value
        )


    private fun addOrUpdateTask(task: ToDoTask) {
        viewModelScope.launch {
            try {
                validateTask(task)
                withContext(Dispatchers.IO) {
                    if (task.id > -1) {
                        repository.updateTask(task)
                    } else {
                        repository.addTask(task.copy(id = 0))
                    }
                }
                _uiEventFlow.emit(DetailTaskNavigation.AfterAddOrUpdateNavigation)
            } catch (e: InvalidTaskException) {
                _uiEventFlow.emit(
                    DetailShowEvent.ShowSnackBar(
                        e.localizedMessage ?: "Unexpected error"
                    )
                )
            }
        }
    }

    @Throws(InvalidTaskException::class)
    private fun validateTask(task: ToDoTask) {
        when {
            task.title.isBlank() -> throw InvalidTaskException("Please fill the title")
            task.description.isBlank() -> throw InvalidTaskException("Please fill the description")
            task.priority == Priority.NONE -> throw InvalidTaskException("Please choose priority")
        }
    }

}