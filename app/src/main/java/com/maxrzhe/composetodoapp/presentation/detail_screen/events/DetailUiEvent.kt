package com.maxrzhe.composetodoapp.presentation.detail_screen.events


sealed interface DetailUiEvent

sealed class DetailShowEvent: DetailUiEvent {
    data class ShowSnackBar(val message: String) : DetailShowEvent()
    data class ShowToast(val message: String) : DetailShowEvent()

}

sealed class DetailTaskNavigation: DetailUiEvent {
    object AfterAddOrUpdateNavigation: DetailTaskNavigation()
}

