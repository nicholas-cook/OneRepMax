package com.nickcook.onerepmax.core.testing.fake

import com.nickcook.onerepmax.core.localdata.filereader.WorkoutDataReader
import com.nickcook.onerepmax.core.localdata.model.WorkoutDataEntry

class FakeWorkoutDataReader : WorkoutDataReader {

    var entriesToReturn: Map<String, List<WorkoutDataEntry>> = emptyMap()
    var exceptionToThrow: Exception? = null

    override suspend fun readWorkoutData(): Map<String, List<WorkoutDataEntry>> {
        exceptionToThrow?.let {
            throw it
        }
        return entriesToReturn
    }
}
