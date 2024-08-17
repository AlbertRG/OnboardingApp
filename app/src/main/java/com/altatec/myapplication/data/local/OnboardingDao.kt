package com.altatec.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface OnboardingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM 'userTable' WHERE userEmail=:email")
    fun getUserByEmail(email: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contact: Contact)

    @Query("SELECT * FROM 'contactTable'")
    fun getContacts(): Flow<List<Contact>>

}