package com.nickcook.onerepmax.core.data.di

import com.nickcook.onerepmax.core.data.repository.WorkoutDataRepository
import com.nickcook.onerepmax.core.data.repository.WorkoutDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface WorkoutDataModule {

    @Binds
    @Singleton
    fun bindsWorkoutDataRepository(workoutDataRepositoryImpl: WorkoutDataRepositoryImpl): WorkoutDataRepository
}
