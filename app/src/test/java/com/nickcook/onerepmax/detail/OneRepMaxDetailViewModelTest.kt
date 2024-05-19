package com.nickcook.onerepmax.detail

import androidx.lifecycle.SavedStateHandle
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.github.mikephil.charting.data.Entry
import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.testing.data.allBackSquatData
import com.nickcook.onerepmax.core.testing.data.allBenchPressData
import com.nickcook.onerepmax.core.testing.data.allDeadliftData
import com.nickcook.onerepmax.core.testing.fake.FakeWorkoutDataRepository
import com.nickcook.onerepmax.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class OneRepMaxDetailViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: OneRepMaxDetailViewModel
    private val workoutDataRepository = FakeWorkoutDataRepository()

    @Test
    fun `successful screen state is returned with correct entries for Back Squats`() {
        viewModel = OneRepMaxDetailViewModel(
            workoutDataRepository,
            SavedStateHandle(mapOf("workoutType" to "Back Squat"))
        )
        workoutDataRepository.workoutTypeResult = Result.Success(allBackSquatData)

        val loadingState = viewModel.screenState.value
        assertThat(loadingState).isInstanceOf(OneRepMaxDetailState.Loading::class)
        assertThat((loadingState as OneRepMaxDetailState.Loading).workoutType).isEqualTo("Back Squat")

        runTest {
            val result = viewModel.screenState.first()
            val expectedData = allBackSquatData.map {
                Entry(
                    it.workoutDate.toFloat(),
                    it.workoutOneRepMax.toFloat()
                )
            }
            assertThat(result).isInstanceOf(OneRepMaxDetailState.Success::class)
            assertThat((result as OneRepMaxDetailState.Success).workoutType).isEqualTo("Back Squat")
            result.data.forEachIndexed { index, entry ->
                assertThat(entry.x).isEqualTo(expectedData[index].x)
                assertThat(entry.y).isEqualTo(expectedData[index].y)
            }
        }
    }

    @Test
    fun `successful screen state is returned with correct entries for Bench Presses`() {
        viewModel = OneRepMaxDetailViewModel(
            workoutDataRepository,
            SavedStateHandle(mapOf("workoutType" to "Barbell Bench Press"))
        )
        workoutDataRepository.workoutTypeResult = Result.Success(allBenchPressData)

        val loadingState = viewModel.screenState.value
        assertThat(loadingState).isInstanceOf(OneRepMaxDetailState.Loading::class)
        assertThat((loadingState as OneRepMaxDetailState.Loading).workoutType).isEqualTo("Barbell Bench Press")

        runTest {
            val result = viewModel.screenState.first()
            val expectedData = allBenchPressData.map {
                Entry(
                    it.workoutDate.toFloat(),
                    it.workoutOneRepMax.toFloat()
                )
            }
            assertThat(result).isInstanceOf(OneRepMaxDetailState.Success::class)
            assertThat((result as OneRepMaxDetailState.Success).workoutType).isEqualTo("Barbell Bench Press")
            result.data.forEachIndexed { index, entry ->
                assertThat(entry.x).isEqualTo(expectedData[index].x)
                assertThat(entry.y).isEqualTo(expectedData[index].y)
            }
        }
    }

    @Test
    fun `successful screen state is returned with correct entries for Deadlifts`() {
        viewModel = OneRepMaxDetailViewModel(
            workoutDataRepository,
            SavedStateHandle(mapOf("workoutType" to "Deadlift"))
        )
        workoutDataRepository.workoutTypeResult = Result.Success(allDeadliftData)

        val loadingState = viewModel.screenState.value
        assertThat(loadingState).isInstanceOf(OneRepMaxDetailState.Loading::class)
        assertThat((loadingState as OneRepMaxDetailState.Loading).workoutType).isEqualTo("Deadlift")

        runTest {
            val result = viewModel.screenState.first()
            val expectedData = allDeadliftData.map {
                Entry(
                    it.workoutDate.toFloat(),
                    it.workoutOneRepMax.toFloat()
                )
            }
            assertThat(result).isInstanceOf(OneRepMaxDetailState.Success::class)
            assertThat((result as OneRepMaxDetailState.Success).workoutType).isEqualTo("Deadlift")
            result.data.forEachIndexed { index, entry ->
                assertThat(entry.x).isEqualTo(expectedData[index].x)
                assertThat(entry.y).isEqualTo(expectedData[index].y)
            }
        }
    }

    @Test
    fun `error screen state is returned when the repository returns an error`() {
        viewModel = OneRepMaxDetailViewModel(
            workoutDataRepository,
            SavedStateHandle(mapOf("workoutType" to "Deadlift"))
        )
        workoutDataRepository.workoutTypeResult = Result.Error("Error")

        val loadingState = viewModel.screenState.value
        assertThat(loadingState).isInstanceOf(OneRepMaxDetailState.Loading::class)
        assertThat((loadingState as OneRepMaxDetailState.Loading).workoutType).isEqualTo("Deadlift")

        runTest {
            val result = viewModel.screenState.first()
            assertThat(result).isInstanceOf(OneRepMaxDetailState.Error::class)
        }
    }
}
