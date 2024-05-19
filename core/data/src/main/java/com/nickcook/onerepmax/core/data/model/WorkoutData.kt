package com.nickcook.onerepmax.core.data.model

import com.nickcook.onerepmax.core.localdata.model.WorkoutDataEntry
import com.nickcook.onerepmax.core.util.Utils

data class WorkoutData(
    val workoutDate: Long,
    val workoutType: String,
    val workoutReps: Int,
    val workoutWeight: Int,
    val workoutOneRepMax: Int
) {
    companion object {
        fun fromWorkoutDataEntry(entry: WorkoutDataEntry): WorkoutData {
            return WorkoutData(
                workoutDate = Utils.getLongFromDateString(entry.workoutDate),
                workoutType = entry.workoutType,
                workoutReps = entry.workoutReps,
                workoutWeight = entry.workoutWeight,
                workoutOneRepMax = Utils.calculateOneRepMax(entry.workoutReps, entry.workoutWeight)
            )
        }
    }
}
