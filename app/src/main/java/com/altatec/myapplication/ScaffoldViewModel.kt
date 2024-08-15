package com.altatec.myapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ScaffoldViewModel:ViewModel() {

    private val _currentScreen: MutableState<Screen.BottomScreen> = mutableStateOf(Screen.BottomScreen.Home)

    val currentScreen: MutableState<Screen.BottomScreen>
        get() = _currentScreen

    fun setCurrentScreen(screen: Screen.BottomScreen){
        _currentScreen.value = screen
    }

}