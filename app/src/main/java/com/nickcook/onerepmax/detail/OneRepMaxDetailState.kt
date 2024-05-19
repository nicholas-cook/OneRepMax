package com.nickcook.onerepmax.detail

import com.github.mikephil.charting.data.Entry

sealed class OneRepMaxDetailState(val workoutType: String) {
    class Loading(workoutType: String) : OneRepMaxDetailState(workoutType)
    class Error(workoutType: String) : OneRepMaxDetailState(workoutType)
    class Success(workoutType: String, val data: List<Entry>, val rmRecord: Int) :
        OneRepMaxDetailState(workoutType)
}
