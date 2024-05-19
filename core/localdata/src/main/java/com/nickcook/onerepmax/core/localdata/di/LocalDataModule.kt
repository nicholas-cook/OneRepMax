package com.nickcook.onerepmax.core.localdata.di

import com.nickcook.onerepmax.core.localdata.filereader.WorkoutDataReader
import com.nickcook.onerepmax.core.localdata.filereader.WorkoutDataReaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {

    @Binds
    @Singleton
    fun bindsWorkoutDataReader(workoutDataReaderImpl: WorkoutDataReaderImpl): WorkoutDataReader
}