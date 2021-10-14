package com.maxrzhe.composetodoapp.core.util

import com.maxrzhe.composetodoapp.presentation.detail_screen.events.AppBarDetailEvent
import com.maxrzhe.composetodoapp.presentation.detail_screen.events.TaskChangeEvent

typealias AppBarDetailClickEvent = (event: AppBarDetailEvent) -> Unit
typealias TaskChangeClickEvent = (event: TaskChangeEvent) -> Unit