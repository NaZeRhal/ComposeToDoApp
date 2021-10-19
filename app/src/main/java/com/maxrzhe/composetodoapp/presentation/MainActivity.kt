package com.maxrzhe.composetodoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.maxrzhe.composetodoapp.presentation.navigation.SetupNavigation
import com.maxrzhe.composetodoapp.presentation.ui.theme.ComposeToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "DBG"

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            ComposeToDoAppTheme {
                Scaffold {
                    SetupNavigation(navController = navController)
                }
            }
        }
    }
}