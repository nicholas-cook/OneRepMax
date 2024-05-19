package com.nickcook.onerepmax.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickcook.onerepmax.R
import com.nickcook.onerepmax.components.OneRepMaxTryAgain
import com.nickcook.onerepmax.core.data.model.WorkoutData
import com.nickcook.onerepmax.ui.theme.OneRepMaxTheme

@Composable
fun OneRepMaxListRoute(
    onItemClick: (workoutType: String) -> Unit,
    viewModel: OneRepMaxListViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    OneRepMaxListScreen(
        screenState = screenState,
        onItemClick = onItemClick,
        onTryAgainClick = viewModel::loadWorkoutData
    )
}

@Composable
fun OneRepMaxListScreen(
    screenState: OneRepMaxListState,
    onItemClick: (workoutType: String) -> Unit,
    onTryAgainClick: () -> Unit
) {
    Scaffold(topBar = { OneRepMaxListTopBar() }) { paddingValues ->
        when (screenState) {
            is OneRepMaxListState.Success -> {
                if (screenState.data.isEmpty()) {
                    OneRepMaxTryAgain(
                        message = stringResource(id = R.string.empty_message),
                        onTryAgainClick = onTryAgainClick
                    )
                    return@Scaffold
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                        .testTag("one_rep_max_list")
                ) {
                    items(screenState.data) { workoutData ->
                        Column(modifier = Modifier.clickable(onClick = { onItemClick(workoutData.workoutType) })) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = workoutData.workoutType,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = "${workoutData.workoutOneRepMax}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Spacer(modifier = Modifier.size(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.label_rm_record),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = stringResource(id = R.string.label_lbs),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }

            is OneRepMaxListState.Error -> {
                OneRepMaxTryAgain(
                    message = stringResource(id = R.string.error_message),
                    onTryAgainClick = onTryAgainClick
                )
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneRepMaxListTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun OneRepMaxListPreview(@PreviewParameter(OneRepMaxListPreviewProvider::class) state: OneRepMaxListState) {
    OneRepMaxTheme {
        OneRepMaxListScreen(
            screenState = state,
            onItemClick = {},
            onTryAgainClick = {}
        )
    }
}

private class OneRepMaxListPreviewProvider : PreviewParameterProvider<OneRepMaxListState> {
    override val values: Sequence<OneRepMaxListState>
        get() = sequenceOf(
            OneRepMaxListState.Success(
                listOf(
                    WorkoutData(
                        workoutDate = System.currentTimeMillis(),
                        workoutType = "Bench Press",
                        workoutReps = 100,
                        workoutWeight = 10,
                        workoutOneRepMax = 200
                    ),
                    WorkoutData(
                        workoutDate = System.currentTimeMillis(),
                        workoutType = "Squat",
                        workoutReps = 100,
                        workoutWeight = 10,
                        workoutOneRepMax = 200
                    ),
                    WorkoutData(
                        workoutDate = System.currentTimeMillis(),
                        workoutType = "Deadlift",
                        workoutReps = 100,
                        workoutWeight = 10,
                        workoutOneRepMax = 200
                    )
                )
            ),
            OneRepMaxListState.Success(emptyList()),
            OneRepMaxListState.Error,
            OneRepMaxListState.Loading
        )
}
