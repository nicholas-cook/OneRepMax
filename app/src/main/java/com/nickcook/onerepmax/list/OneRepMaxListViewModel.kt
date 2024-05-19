package com.nickcook.onerepmax.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickcook.onerepmax.core.data.model.Result
import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.core.data.repository.WorkoutDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneRepMaxListViewModel @Inject constructor(private val workoutDataRepository: WorkoutDataRepository) :
    ViewModel() {

    private val _screenState = MutableStateFlow<OneRepMaxListState>(OneRepMaxListState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        loadWorkoutData()
    }

    fun loadWorkoutData() {
        viewModelScope.launch {
            when (val workoutData = workoutDataRepository.getWorkoutData()) {
                is Result.Success -> {
                    _screenState.value =
                        OneRepMaxListState.Success(parseWorkoutData(workoutData.data))
                }

                is Result.Error -> {
                    _screenState.value = OneRepMaxListState.Error
                }
            }
        }
    }

    private fun parseWorkoutData(workoutData: Map<String, List<WorkoutData>>): List<WorkoutData> {
        val maxOneRepMaxes = mutableListOf<WorkoutData>()
        workoutData.forEach { (_, workoutList) ->
            workoutList.sortedByDescending { it.workoutDate }.maxByOrNull { it.workoutOneRepMax }
                ?.let {
                    maxOneRepMaxes.add(it)
                }
        }
        return maxOneRepMaxes.sortedBy { it.workoutType }
    }
}