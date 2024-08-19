package com.altatec.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altatec.myapplication.data.local.entity.User
import com.altatec.myapplication.domain.GetUserByEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) : ViewModel() {

    private var _user by mutableStateOf<User?>(null)

    var emailSuppText by mutableStateOf("")
    var emailError by mutableStateOf(false)
    var passwordSuppText by mutableStateOf("")
    var passwordError by mutableStateOf(false)
    var goToScaffold by mutableStateOf(false)

    fun onLoginClick(email: String, password: String) {
        resetSuppTextAndErrors()
        viewModelScope.launch {
            if (isEmailRegister(email)) {
                emailError = false
                passwordError = false
                emailSuppText = ""
                passwordSuppText = ""
                if (isPasswordCorrect(password)) {
                    goToScaffold = true
                } else {
                    passwordSuppText = "Password incorrect"
                    passwordError = true
                }
            } else {
                emailSuppText = "Email Not Register"
                emailError = true
            }
        }
    }

    private suspend fun isEmailRegister(email: String): Boolean {
        _user = getUserByEmail(email).firstOrNull()
        return _user != null
    }

    private fun getUserByEmail(email: String): Flow<User> {
        return getUserByEmailUseCase.getUserByEmail(email)
    }

    private fun isPasswordCorrect(password: String): Boolean {
        return _user?.password == password
    }

    private fun resetSuppTextAndErrors(){
        emailSuppText = ""
        emailError = false
        passwordSuppText = ""
        passwordError = false
    }

}