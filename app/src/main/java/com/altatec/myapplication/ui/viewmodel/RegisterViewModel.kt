package com.altatec.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altatec.myapplication.data.local.entity.User
import com.altatec.myapplication.domain.InsertUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            insertUserUseCase.insertUser(user = user)
        }
    }

}