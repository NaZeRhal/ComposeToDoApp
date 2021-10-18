package com.maxrzhe.composetodoapp.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xfffcfcfc)
val MediumGray = Color(0xff9c9c9c)
val DarkGray = Color(0xff141414)

val LowPriorityColor = Color(0xff00c980)
val MediumPriorityColor = Color(0xffffc114)
val HighPriorityColor = Color(0xffff4646)
val NonePriorityColor = Color(0xff9c9c9c)

val FabDarkBackgroundColor = Color(0xFF148423)

val Colors.splashScreenBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple700 else Color.Black

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else Color.LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

val Colors.fabAddBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else FabDarkBackgroundColor

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.focusedBorderColor: Color
    @Composable
    get() = if (isLight) Purple700 else MediumPriorityColor

val Colors.focusedLabelColor: Color
    @Composable
    get() = if (isLight) Purple700 else MediumPriorityColor