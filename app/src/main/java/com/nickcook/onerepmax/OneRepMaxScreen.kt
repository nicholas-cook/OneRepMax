package com.nickcook.onerepmax

sealed class OneRepMaxScreen(val route: String) {
    data object OneRepMaxListScreen : OneRepMaxScreen("oneRepMaxList")
    data object OneRepMaxDetailScreen : OneRepMaxScreen("oneRepMaxDetail")
}