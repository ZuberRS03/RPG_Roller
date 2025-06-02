package com.example.rpghelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.rpghelper.ui.theme.RPGHelperTheme

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

import com.example.rpghelper.presentation.navigation.AppNavigation
import com.example.rpghelper.presentation.navigation.components.BottomNavigationBar
import com.example.rpghelper.presentation.screen_history.HistoryViewModel
import com.example.rpghelper.presentation.screen_main.MainViewModel
import com.example.rpghelper.presentation.screen_presets.PresetsViewModel

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    private var shakeThreshold = 12f
    private var lastShakeTime = 0L
    private lateinit var sensorListener: SensorEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AppModule.provideRepository(applicationContext)
        val mainViewModel = MainViewModel(repository)
        val historyViewModel = HistoryViewModel(repository)
        val presetsViewModel = PresetsViewModel(repository)

        // Konfiguracja akcelerometru
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]
                    val acceleration = sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH

                    if (acceleration > shakeThreshold) {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastShakeTime > 1000) {
                            lastShakeTime = currentTime
                            mainViewModel.animateRollDice()
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            sensorListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )

        // UI i nawigacja
        setContent {
            val navController = rememberNavController()
            RPGHelperTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            onNavigate = { navController.navigate(it) }
                        )
                    }
                ) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        historyViewModel = historyViewModel,
                        presetsViewModel = presetsViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorListener)
    }
}

