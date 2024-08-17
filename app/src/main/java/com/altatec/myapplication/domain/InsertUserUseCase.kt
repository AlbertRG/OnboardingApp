package com.altatec.myapplication.domain

import com.altatec.myapplication.data.OnboardingLocalRepository
import com.altatec.myapplication.data.local.entity.User

class InsertUserUseCase(
    private val repository: OnboardingLocalRepository
) {
    suspend fun insertUser(user: User) {
        return repository.insertUser(user)
    }
}