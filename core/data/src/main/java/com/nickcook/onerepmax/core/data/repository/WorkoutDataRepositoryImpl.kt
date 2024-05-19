package com.nickcook.onerepmax.core.data.repository

import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.core.localdata.filereader.WorkoutDataReader
import timber.log.Timber
import javax.inject.Inject

class WorkoutDataRepositoryImpl @Inject constructor(private val workoutDataReader: WorkoutDataReader) : WorkoutDataRepository {

    private var currentWorkoutData: Map<String, List<WorkoutData>>? = null

    override suspend fun getWorkoutData(): Result<Map<String, List<WorkoutData>>> {
        currentWorkoutData?.let {
            return Result.Success(it)
        }
        return try {
            val workoutData = mutableMapOf<String, List<WorkoutData>>()
            val dataFromFile = workoutDataReader.readWorkoutData()
            dataFromFile.keys.forEach { key ->
                val data = dataFromFile[key]?.map { WorkoutData.fromWorkoutDataEntry(it) }
                data?.let {
                    workoutData[key] = it
                }
            }
            currentWorkoutData = workoutData

            Result.Success(workoutData)
        } catch (e: Exception) {
            Timber.e(e, "Error reading workout data")
            Result.Error("Error reading workout data")
        }
    }

    override suspend fun getDataForWorkoutType(workoutType: String): Result<List<WorkoutData>> {
        val workoutData = getWorkoutData()
        return if (workoutData is Result.Error) {
            workoutData
        } else {
            val data = workoutData as Result.Success
            val workoutTypeData = data.data[workoutType]
            if (workoutTypeData == null) {
                Result.Error("No data found for workout type: $workoutType")
            } else {
                Result.Success(workoutTypeData)
            }
        }
    }
}