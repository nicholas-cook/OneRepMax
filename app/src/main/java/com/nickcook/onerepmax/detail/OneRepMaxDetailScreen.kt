package com.nickcook.onerepmax.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.nickcook.onerepmax.R
import com.nickcook.onerepmax.components.OneRepMaxTryAgain
import com.nickcook.onerepmax.core.util.Utils
import com.nickcook.onerepmax.ui.theme.OneRepMaxTheme

@Composable
fun OneRepMaxDetailRoute(
    onNavigateUp: () -> Unit,
    viewModel: OneRepMaxDetailViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    OneRepMaxDetailScreen(
        screenState = screenState,
        onTryAgainClick = viewModel::loadDataForWorkoutType,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun OneRepMaxDetailScreen(
    screenState: OneRepMaxDetailState,
    onTryAgainClick: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(topBar = {
        OneRepMaxDetailTopBar(
            screenState.workoutType,
            onNavigateUp
        )
    }) { paddingValues ->
        when (screenState) {
            is OneRepMaxDetailState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = screenState.workoutType,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${screenState.rmRecord}",
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
                        Text(
                            text = stringResource(id = R.string.label_lbs),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    if (screenState.data.isEmpty()) {
                        OneRepMaxTryAgain(
                            message = stringResource(id = R.string.empty_message),
                            onTryAgainClick = onTryAgainClick
                        )
                    } else {
                        OneRepMaxLineChart(screenState = screenState)
                    }
                }
            }

            is OneRepMaxDetailState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is OneRepMaxDetailState.Error -> {
                OneRepMaxTryAgain(
                    message = stringResource(id = R.string.error_message),
                    onTryAgainClick = onTryAgainClick
                )
            }
        }
    }
}

@Composable
private fun OneRepMaxLineChart(screenState: OneRepMaxDetailState.Success) {
    val axisLeftFormat = stringResource(id = R.string.axis_label_lbs)
    val lineColor = MaterialTheme.colorScheme.primary.toArgb()
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()
    AndroidView(modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val chart = LineChart(context)
            val dataSet = LineDataSet(screenState.data, "").apply {
                color = lineColor
                setDrawCircleHole(false)
                setCircleColor(lineColor)
            }
            chart.data = LineData(dataSet).apply {
                setDrawValues(false)
            }

            // Hide unused chart elements
            with(chart) {
                legend.isEnabled = false
                setTouchEnabled(false)
                description.isEnabled = false
                axisRight.isEnabled = false
            }

            // Configure x-axis
            with(chart.xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                setLabelCount(5, true)
                this.textColor = textColor
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return Utils.getDateStringFromLong(value.toLong())
                    }
                }
            }

            // Configure y-axis
            with(chart.axisLeft) {
                setLabelCount(5, false)
                this.textColor = textColor
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return String.format(axisLeftFormat, value.toInt())
                    }
                }
            }

            chart.invalidate()
            chart
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OneRepMaxDetailTopBar(title: String, onNavigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
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
            ),
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_nav_back)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun OneRepMaxDetailPreview(@PreviewParameter(OneRepMaxDetailPreviewProvider::class) state: OneRepMaxDetailState) {
    OneRepMaxTheme {
        OneRepMaxDetailScreen(
            screenState = state,
            onTryAgainClick = {},
            onNavigateUp = {}
        )
    }
}

private class OneRepMaxDetailPreviewProvider : PreviewParameterProvider<OneRepMaxDetailState> {
    override val values: Sequence<OneRepMaxDetailState>
        get() = sequenceOf(
            OneRepMaxDetailState.Success(
                workoutType = "Bench Press",
                rmRecord = 225,
                data = listOf(
                    Entry(1.62999999E12f, 185f),
                    Entry(1.75600003E12f, 190f),
                    Entry(1.82350001E12f, 200f),
                    Entry(1.88799989E12f, 210f),
                    Entry(1.94599898E12f, 215f)
                )
            ),
            OneRepMaxDetailState.Success(
                workoutType = "Bench Press",
                rmRecord = 225,
                data = emptyList()
            ),
            OneRepMaxDetailState.Loading("Bench Press"),
            OneRepMaxDetailState.Error("Bench Press")
        )
}
