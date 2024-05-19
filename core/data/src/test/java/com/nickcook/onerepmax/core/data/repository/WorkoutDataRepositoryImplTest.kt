package com.nickcook.onerepmax.core.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.core.testing.data.backSquatEntry1
import com.nickcook.onerepmax.core.testing.data.backSquatEntry2
import com.nickcook.onerepmax.core.testing.data.backSquatEntry3
import com.nickcook.onerepmax.core.testing.data.benchPressEntry1
import com.nickcook.onerepmax.core.testing.data.benchPressEntry2
import com.nickcook.onerepmax.core.testing.data.benchPressEntry3
import com.nickcook.onerepmax.core.testing.data.deadliftEntry1
import com.nickcook.onerepmax.core.testing.data.deadliftEntry2
import com.nickcook.onerepmax.core.testing.data.deadliftEntry3
import com.nickcook.onerepmax.core.testing.fake.FakeWorkoutDataReader
import kotlinx.coroutines.test.runTest
import org.junit.Test

class WorkoutDataRepositoryImplTest {

    private val workoutDataReader = FakeWorkoutDataReader()
    private val workoutDataRepository = WorkoutDataRepositoryImpl(workoutDataReader)

    @Test
    fun `when reader returns empty map, repository returns success result with empty map`() =
        runTest {
            workoutDataReader.entriesToReturn = emptyMap()
            workoutDataReader.exceptionToThrow = null

            val result = workoutDataRepository.getWorkoutData()
            assertThat(result).isEqualTo(Result.Success(emptyMap()))
        }

    @Test
    fun `when reader returns workout entries, repository returns success result with map of workout data`() =
        runTest {
            workoutDataReader.entriesToReturn = mapOf(
                "Back Squat" to listOf(backSquatEntry1, backSquatEntry2, backSquatEntry3),
                "Barbell Bench Press" to listOf(
                    benchPressEntry1,
                    benchPressEntry2,
                    benchPressEntry3
                ),
                "Deadlift" to listOf(deadliftEntry1, deadliftEntry2, deadliftEntry3)
            )
            workoutDataReader.exceptionToThrow = null

            val expectedData = mapOf(
                "Back Squat" to listOf(
                    backSquatEntry1,
                    backSquatEntry2,
                    backSquatEntry3
                ).map { WorkoutData.fromWorkoutDataEntry(it) },
                "Barbell Bench Press" to listOf(
                    benchPressEntry1,
                    benchPressEntry2,
                    benchPressEntry3
                ).map { WorkoutData.fromWorkoutDataEntry(it) },
                "Deadlift" to listOf(
                    deadliftEntry1,
                    deadliftEntry2,
                    deadliftEntry3
                ).map { WorkoutData.fromWorkoutDataEntry(it) }
            )
            val result = workoutDataRepository.getWorkoutData()
            assertThat(result).isEqualTo(Result.Success(expectedData))
        }

    @Test
    fun `when an exception occurs reading data, repository returns error result`() = runTest {
        workoutDataReader.exceptionToThrow = Exception()
        val result = workoutDataRepository.getWorkoutData()
        assertThat(result).isInstanceOf(Result.Error::class)
    }

    @Test
    fun `when Back Squat data is available, repository returns success result with Back Squat data`() =
        runTest {
            workoutDataReader.entriesToReturn = mapOf(
                "Back Squat" to listOf(backSquatEntry1, backSquatEntry2, backSquatEntry3),
                "Barbell Bench Press" to listOf(
                    benchPressEntry1,
                    benchPressEntry2,
                    benchPressEntry3
                ),
                "Deadlift" to listOf(deadliftEntry1, deadliftEntry2, deadliftEntry3)
            )
            workoutDataReader.exceptionToThrow = null

            val expectedData = listOf(
                backSquatEntry1,
                backSquatEntry2,
                backSquatEntry3
            ).map { WorkoutData.fromWorkoutDataEntry(it) }
            val result = workoutDataRepository.getDataForWorkoutType("Back Squat")
            assertThat(result).isEqualTo(Result.Success(expectedData))
        }

    @Test
    fun `when Bench Press data is available, repository returns success result with Bench Press data`() =
        runTest {
            workoutDataReader.entriesToReturn = mapOf(
                "Back Squat" to listOf(backSquatEntry1, backSquatEntry2, backSquatEntry3),
                "Barbell Bench Press" to listOf(
                    benchPressEntry1,
                    benchPressEntry2,
                    benchPressEntry3
                ),
                "Deadlift" to listOf(deadliftEntry1, deadliftEntry2, deadliftEntry3)
            )
            workoutDataReader.exceptionToThrow = null

            val expectedData = listOf(
                benchPressEntry1,
                benchPressEntry2,
                benchPressEntry3
            ).map { WorkoutData.fromWorkoutDataEntry(it) }
            val result = workoutDataRepository.getDataForWorkoutType("Barbell Bench Press")
            assertThat(result).isEqualTo(Result.Success(expectedData))
        }

    @Test
    fun `when Deadlift data is available, repository returns success result with Deadlift data`() =
        runTest {
            workoutDataReader.entriesToReturn = mapOf(
                "Back Squat" to listOf(backSquatEntry1, backSquatEntry2, backSquatEntry3),
                "Barbell Bench Press" to listOf(
                    benchPressEntry1,
                    benchPressEntry2,
                    benchPressEntry3
                ),
                "Deadlift" to listOf(deadliftEntry1, deadliftEntry2, deadliftEntry3)
            )
            workoutDataReader.exceptionToThrow = null

            val expectedData = listOf(
                deadliftEntry1,
                deadliftEntry2,
                deadliftEntry3
            ).map { WorkoutData.fromWorkoutDataEntry(it) }
            val result = workoutDataRepository.getDataForWorkoutType("Deadlift")
            assertThat(result).isEqualTo(Result.Success(expectedData))
        }

    @Test
    fun `when no data is available for a workout type, repository returns error result`() =
        runTest {
            workoutDataReader.entriesToReturn = mapOf(
                "Back Squat" to listOf(backSquatEntry1, backSquatEntry2, backSquatEntry3),
                "Deadlift" to listOf(deadliftEntry1, deadliftEntry2, deadliftEntry3)
            )
            workoutDataReader.exceptionToThrow = null

            val result = workoutDataRepository.getDataForWorkoutType("Barbell Bench Press")
            assertThat(result).isInstanceOf(Result.Error::class)
        }
}
