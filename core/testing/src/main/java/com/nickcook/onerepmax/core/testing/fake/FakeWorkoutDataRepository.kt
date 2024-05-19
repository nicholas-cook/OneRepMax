package com.nickcook.onerepmax.core.testing.fake

import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.core.data.repository.WorkoutDataRepository
import com.nickcook.onerepmax.core.testing.data.allBackSquatData
import com.nickcook.onerepmax.core.testing.data.allWorkoutData

class FakeWorkoutDataRepository : WorkoutDataRepository {

    var workoutDataResult: Result<Map<String, List<WorkoutData>>> = Result.Success(allWorkoutData)
    var workoutTypeResult: Result<List<WorkoutData>> = Result.Success(allBackSquatData)

    override suspend fun getWorkoutData(): Result<Map<String, List<WorkoutData>>> =
        workoutDataResult

    override suspend fun getDataForWorkoutType(workoutType: String): Result<List<WorkoutData>> =
        workoutTypeResult
}