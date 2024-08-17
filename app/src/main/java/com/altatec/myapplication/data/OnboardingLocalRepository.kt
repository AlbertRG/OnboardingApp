package com.altatec.myapplication.data

import com.altatec.myapplication.data.local.OnboardingDao
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.data.local.entity.User
import kotlinx.coroutines.flow.Flow

class OnboardingLocalRepository(
    private val onboardingDao: OnboardingDao,
) {

    suspend fun insertUser(user: User) {
        onboardingDao.insertUser(user)
    }

    fun getUserByEmail(email: String): Flow<User> {
        return onboardingDao.getUserByEmail(email)
    }

    suspend fun insertContact(contact: Contact){
        onboardingDao.insertContact(contact)
    }

    fun getContacts(): Flow<List<Contact>> {
        return onboardingDao.getContacts()
    }

}