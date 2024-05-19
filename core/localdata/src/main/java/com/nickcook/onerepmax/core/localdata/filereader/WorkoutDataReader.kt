package com.nickcook.onerepmax.core.localdata.filereader

import com.nickcook.onerepmax.core.localdata.model.WorkoutDataEntry

interface WorkoutDataReader {

    suspend fun readWorkoutData(): Map<String, List<WorkoutDataEntry>>
}