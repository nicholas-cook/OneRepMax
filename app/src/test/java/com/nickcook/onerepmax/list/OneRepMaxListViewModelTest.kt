package com.nickcook.onerepmax.list

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.testing.data.backSquat3
import com.nickcook.onerepmax.core.testing.data.benchPress3
import com.nickcook.onerepmax.core.testing.data.deadlift3
import com.nickcook.onerepmax.core.testing.fake.FakeWorkoutDataRepository
import com.nickcook.onerepmax.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class OneRepMaxListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: OneRepMaxListViewModel
    private val repository = FakeWorkoutDataRepository()

    @Test
    fun `successful screen state is returned with the 1 RM record for each workout type in the correct order`() {
        viewModel = OneRepMaxListViewModel(repository)

        assertThat(viewModel.screenState.value).isEqualTo(OneRepMaxListState.Loading)

        runTest {
            val result = viewModel.screenState.first()

            assertThat(result).isEqualTo(
                OneRepMaxListState.Success(
                    listOf(
                        backSquat3,
                        benchPress3,
                        deadlift3
                    )
                )
            )
        }
    }

    @Test
    fun `error screen state is returned when the repository returns an error`() {
        viewModel = OneRepMaxListViewModel(repository)
        repository.workoutDataResult = Result.Error("Error")

        assertThat(viewModel.screenState.value).isEqualTo(OneRepMaxListState.Loading)

        runTest {
            val result = viewModel.screenState.first()

            assertThat(result).isInstanceOf(OneRepMaxListState.Error::class)
        }
    }
}
