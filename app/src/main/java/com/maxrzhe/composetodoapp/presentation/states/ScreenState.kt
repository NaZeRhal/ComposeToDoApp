package com.maxrzhe.composetodoapp.presentation.states

sealed interface ScreenState<out T>

sealed class CommonScreenState<out T> : ScreenState<T> {
    object Loading : CommonScreenState<Nothing>()
    object Idle : CommonScreenState<Nothing>()
    data class Error(val error: Throwable) : CommonScreenState<Nothing>()
}

sealed class SuccessState<out T>: ScreenState<T> {
    object Completed: SuccessState<Nothing>()
    data class WithData<T>(val data: T): SuccessState<T>()
}
