package com.altatec.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.altatec.myapplication.data.local.converter.Converter
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.data.local.entity.User

@Database(
    entities = [User::class, Contact::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converter::class
)
abstract class OnboardingDatabase : RoomDatabase() {
    abstract val onboardingDao: OnboardingDao
}