package com.nickcook.onerepmax.list

import com.nickcook.onerepmax.core.data.model.WorkoutData


sealed class OneRepMaxListState {
    data object Loading : OneRepMaxListState()
    data object Error : OneRepMaxListState()
    data class Success(val data: List<WorkoutData>) : OneRepMaxListState()
}