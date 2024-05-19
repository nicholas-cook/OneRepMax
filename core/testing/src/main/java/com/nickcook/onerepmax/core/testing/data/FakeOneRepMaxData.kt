package com.nickcook.onerepmax.core.testing.data

import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.core.localdata.model.WorkoutDataEntry

val backSquat1 = WorkoutData(
    workoutDate = 1704085200,
    workoutType = "Back Squat",
    workoutReps = 5,
    workoutWeight = 200,
    workoutOneRepMax = 225
)

val backSquat2 = WorkoutData(
    workoutDate = 1704690000,
    workoutType = "Back Squat",
    workoutReps = 5,
    workoutWeight = 250,
    workoutOneRepMax = 275
)

val backSquat3 = WorkoutData(
    workoutDate = 1705294800,
    workoutType = "Back Squat",
    workoutReps = 5,
    workoutWeight = 300,
    workoutOneRepMax = 325
)

val benchPress1 = WorkoutData(
    workoutDate = 1704085200,
    workoutType = "Barbell Bench Press",
    workoutReps = 5,
    workoutWeight = 200,
    workoutOneRepMax = 225
)

val benchPress2 = WorkoutData(
    workoutDate = 1704690000,
    workoutType = "Barbell Bench Press",
    workoutReps = 5,
    workoutWeight = 250,
    workoutOneRepMax = 275
)

val benchPress3 = WorkoutData(
    workoutDate = 1705294800,
    workoutType = "Barbell Bench Press",
    workoutReps = 5,
    workoutWeight = 300,
    workoutOneRepMax = 321
)

val deadlift1 = WorkoutData(
    workoutDate = 1704085200,
    workoutType = "Deadlift",
    workoutReps = 5,
    workoutWeight = 200,
    workoutOneRepMax = 225
)

val deadlift2 = WorkoutData(
    workoutDate = 1704690000,
    workoutType = "Deadlift",
    workoutReps = 5,
    workoutWeight = 250,
    workoutOneRepMax = 275
)

val deadlift3 = WorkoutData(
    workoutDate = 1705294800,
    workoutType = "Deadlift",
    workoutReps = 5,
    workoutWeight = 300,
    workoutOneRepMax = 320
)

val allBackSquatData = listOf(backSquat1, backSquat2, backSquat3)

val allBenchPressData = listOf(benchPress1, benchPress2, benchPress3)

val allDeadliftData = listOf(deadlift1, deadlift2, deadlift3)

val allWorkoutData = mapOf(
    "Back Squat" to allBackSquatData,
    "Barbell Bench Press" to allBenchPressData,
    "Deadlift" to allDeadliftData
)

val backSquatEntry1 = WorkoutDataEntry(
    workoutDate = "Oct 15 2019",
    workoutType = "Back Squat",
    workoutReps = 3,
    workoutWeight = 225
)

val backSquatEntry2 = WorkoutDataEntry(
    workoutDate = "Oct 29 2019",
    workoutType = "Back Squat",
    workoutReps = 2,
    workoutWeight = 285
)

val backSquatEntry3 = WorkoutDataEntry(
    workoutDate = "Nov 05 2019",
    workoutType = "Back Squat",
    workoutReps = 3,
    workoutWeight = 240
)

val benchPressEntry1 = WorkoutDataEntry(
    workoutDate = "Oct 28 2019",
    workoutType = "Barbell Bench Press",
    workoutReps = 2,
    workoutWeight = 215
)

val benchPressEntry2 = WorkoutDataEntry(
    workoutDate = "Nov 03 2019",
    workoutType = "Barbell Bench Press",
    workoutReps = 5,
    workoutWeight = 185
)

val benchPressEntry3 = WorkoutDataEntry(
    workoutDate = "Nov 07 2019",
    workoutType = "Barbell Bench Press",
    workoutReps = 10,
    workoutWeight = 155
)

val deadliftEntry1 = WorkoutDataEntry(
    workoutDate = "Oct 27 2019",
    workoutType = "Barbell Bench Press",
    workoutReps = 3,
    workoutWeight = 305
)

val deadliftEntry2 = WorkoutDataEntry(
    workoutDate = "Nov 03 2019",
    workoutType = "Barbell Bench Press",
    workoutReps = 5,
    workoutWeight = 295
)

val deadliftEntry3 = WorkoutDataEntry(
    workoutDate = "Nov 07 2019",
    workoutType = "Barbell Bench Press",
    workoutReps = 4,
    workoutWeight = 305
)
