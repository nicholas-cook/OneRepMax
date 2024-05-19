package com.nickcook.onerepmax.core.data.repository

import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.data.model.WorkoutData

interface WorkoutDataRepository {

    suspend fun getWorkoutData(): Result<Map<String, List<WorkoutData>>>

    suspend fun getDataForWorkoutType(workoutType: String): Result<List<WorkoutData>>
}