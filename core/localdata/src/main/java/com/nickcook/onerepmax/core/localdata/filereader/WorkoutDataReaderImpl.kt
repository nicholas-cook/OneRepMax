package com.nickcook.onerepmax.core.localdata.filereader

import android.content.Context
import com.nickcook.onerepmax.core.localdata.model.WorkoutDataEntry
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class WorkoutDataReaderImpl @Inject constructor(@ApplicationContext private val context: Context) :
    WorkoutDataReader {

    override suspend fun readWorkoutData(): Map<String, List<WorkoutDataEntry>> {
        val workoutData = mutableMapOf<String, List<WorkoutDataEntry>>()
        val file = context.assets.open(FILE_NAME)
        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                try {
                    val data = line.split(",")
                    val workoutDate = data[0]
                    val workoutType = data[1]
                    val workoutReps = data[2].toInt()
                    val workoutWeight = data[3].toInt()
                    val entry =
                        WorkoutDataEntry(workoutDate, workoutType, workoutReps, workoutWeight)

                    val currentList = workoutData[workoutType]?.toMutableList() ?: mutableListOf()
                    currentList.add(entry)
                    workoutData[workoutType] = currentList
                } catch (e: Exception) {
                    Timber.e(t = e, message = "Error parsing workout data line: $line")
                }
            }
        }

        return workoutData
    }

    companion object {
        private const val FILE_NAME = "workoutData.txt"
    }
}