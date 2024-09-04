package com.altatec.myapplication.domain

import com.altatec.myapplication.data.OnboardingLocalRepository
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.data.local.entity.User
import kotlinx.coroutines.flow.Flow

class GetContactsUseCase(
    private val repository: OnboardingLocalRepository
) {
    fun getContacts(): Flow<List<Contact>> {
        return repository.getContacts()
    }
}