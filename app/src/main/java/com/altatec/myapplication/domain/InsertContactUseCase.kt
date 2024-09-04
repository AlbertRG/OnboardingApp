package com.altatec.myapplication.domain

import com.altatec.myapplication.data.OnboardingLocalRepository
import com.altatec.myapplication.data.local.entity.Contact

class InsertContactUseCase(
    private val repository: OnboardingLocalRepository
) {
    suspend fun insertContact(contact: Contact) {
        return repository.insertContact(contact)
    }
}