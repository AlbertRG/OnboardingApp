package com.altatec.myapplication.domain

import com.altatec.myapplication.data.OnboardingLocalRepository
import com.altatec.myapplication.data.local.entity.User
import kotlinx.coroutines.flow.Flow

class GetUserByEmailUseCase(
    private val repository: OnboardingLocalRepository
) {
    fun getUserByEmail(email: String): Flow<User> {
        return repository.getUserByEmail(email)
    }
}