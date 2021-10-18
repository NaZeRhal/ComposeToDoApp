package com.maxrzhe.composetodoapp.core.util

import com.maxrzhe.composetodoapp.presentation.detail_screen.events.DetailScreenEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.TaskChangeEvent

typealias AppBarDetailClickEvent = (event: DetailScreenEvent) -> Unit
typealias TaskChangeClickEvent = (event: TaskChangeEvent) -> Unit