package com.altatec.myapplication.module

import android.app.Application
import androidx.room.Room
import com.altatec.myapplication.data.OnboardingLocalRepository
import com.altatec.myapplication.data.local.OnboardingDao
import com.altatec.myapplication.data.local.OnboardingDatabase
import com.altatec.myapplication.domain.GetUserByEmailUseCase
import com.altatec.myapplication.domain.InsertUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOnboardingDao(application: Application): OnboardingDao {
        val db = Room.databaseBuilder(
            application,
            OnboardingDatabase::class.java,
            "onboarding.db"
        ).build()
        return db.onboardingDao
    }

    @Provides
    @Singleton
    fun provideLocalRepository(onboardingDao: OnboardingDao): OnboardingLocalRepository {
        return OnboardingLocalRepository(onboardingDao)
    }

    @Provides
    @Singleton
    fun provideInsertUser(repository: OnboardingLocalRepository): InsertUserUseCase {
        return InsertUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserByEmail(repository: OnboardingLocalRepository): GetUserByEmailUseCase {
        return GetUserByEmailUseCase(repository)
    }

}