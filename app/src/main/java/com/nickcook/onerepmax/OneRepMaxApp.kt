package com.nickcook.onerepmax

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nickcook.onerepmax.detail.OneRepMaxDetailRoute
import com.nickcook.onerepmax.list.OneRepMaxListRoute
import com.nickcook.onerepmax.ui.theme.OneRepMaxTheme

@Composable
fun OneRepMaxApp(navHostController: NavHostController) {
    OneRepMaxTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OneRepMaxNavHost(navHostController = navHostController)
        }
    }
}

@Composable
fun OneRepMaxNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = OneRepMaxScreen.OneRepMaxListScreen.route
    ) {
        composable(route = OneRepMaxScreen.OneRepMaxListScreen.route) {
            OneRepMaxListRoute(onItemClick = { workoutType ->
                navHostController.navigate(route = "${OneRepMaxScreen.OneRepMaxDetailScreen.route}/$workoutType")
            })
        }
        composable(route = "${OneRepMaxScreen.OneRepMaxDetailScreen.route}/{workoutType}") {
            OneRepMaxDetailRoute(onNavigateUp = { navHostController.popBackStack() })
        }
    }
}