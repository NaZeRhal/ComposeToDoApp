package com.maxrzhe.composetodoapp.data.models

import androidx.compose.ui.graphics.Color
import com.maxrzhe.composetodoapp.presentation.ui.theme.HighPriorityColor
import com.maxrzhe.composetodoapp.presentation.ui.theme.LowPriorityColor
import com.maxrzhe.composetodoapp.presentation.ui.theme.MediumPriorityColor
import com.maxrzhe.composetodoapp.presentation.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    NONE(NonePriorityColor),
    LOW(LowPriorityColor),
    MEDIUM(MediumPriorityColor),
    HIGH(HighPriorityColor)
}