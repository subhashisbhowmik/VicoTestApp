package com.test.vicotestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.test.vicotestapp.ui.theme.VicoTestAppTheme
import com.test.vicotestapp.ui.vm.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    private val producer = ChartEntryModelProducer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VicoTestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel by viewModels<MainViewModel>()
                    Chart(
                        chart = lineChart(),
                        chartModelProducer = producer,
                        startAxis = rememberStartAxis(),
                        bottomAxis = rememberBottomAxis(),
                        diffAnimationSpec = null
                    ) { 3F }

                    LaunchedEffect(key1 = Unit) {
                        while (true) {
                            launch (Dispatchers.IO){
                                x += 0.02F
                                viewModel.data.add(FloatEntry(x, sin(x * 2 * PI.toFloat())))
                                while (viewModel.data.size > 3080) viewModel.data.removeFirst()
                                producer.setEntries(viewModel.data.toList())
                            }
                            delay(5)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private var x = 0F
    }
}
