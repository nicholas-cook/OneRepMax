package com.nickcook.onerepmax.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.core.data.repository.WorkoutDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneRepMaxDetailViewModel @Inject constructor(
    private val workoutDataRepository: WorkoutDataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _workoutType = savedStateHandle["workoutType"] ?: ""

    private val _screenState =
        MutableStateFlow<OneRepMaxDetailState>(OneRepMaxDetailState.Loading(_workoutType))
    val screenState = _screenState.asStateFlow()

    init {
        loadDataForWorkoutType()
    }

    fun loadDataForWorkoutType() {
        viewModelScope.launch {
            val dataForWorkoutType = workoutDataRepository.getDataForWorkoutType(_workoutType)
            when (dataForWorkoutType) {
                is Result.Success -> {
                    val chartEntries = getMaxOneRepMaxForEachDay(dataForWorkoutType.data)
                    _screenState.value = OneRepMaxDetailState.Success(
                        _workoutType,
                        chartEntries,
                        dataForWorkoutType.data.maxOf { it.workoutOneRepMax })
                }

                else -> {
                    _screenState.value = OneRepMaxDetailState.Error(_workoutType)
                }
            }
        }
    }

    private fun getMaxOneRepMaxForEachDay(workoutData: List<WorkoutData>): List<Entry> {
        val maxOneRepMaxForEachDay = mutableListOf<Entry>()
        // Split the data into lists of the same day
        val workoutDataByDay = workoutData.groupBy { it.workoutDate }
        // Find the max one rep max for each day
        workoutDataByDay.forEach { (_, data) ->
            data.maxByOrNull { it.workoutOneRepMax }?.let {
                maxOneRepMaxForEachDay.add(
                    Entry(
                        it.workoutDate.toFloat(),
                        it.workoutOneRepMax.toFloat()
                    )
                )
            }
        }
        return maxOneRepMaxForEachDay.sortedBy { it.x }
    }
}
