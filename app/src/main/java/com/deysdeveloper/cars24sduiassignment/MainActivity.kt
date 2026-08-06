package com.deysdeveloper.cars24sduiassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.deysdeveloper.cars24sduiassignment.navigation.AppNavigation
import com.deysdeveloper.cars24sduiassignment.ui.theme.Cars24SDUIAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appStartMs = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SDUI_PERF", "MainActivity.onCreate — app start epoch: ${appStartMs}ms")
        enableEdgeToEdge()
        setContent {
            Cars24SDUIAssignmentTheme {
                AppNavigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
