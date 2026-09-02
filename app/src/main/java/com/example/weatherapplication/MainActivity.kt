package com.example.weatherapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApplicationTheme(){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Weatherrapp()
                }
            }
        }
    }
}

data class Weather(
    val temperature: String,
    val humidity: String,
    val windspeed: String
)

data class Forecast(
    val date: String,
    val temperature: String
)

@Composable
fun Weatherrapp() {
    var r by remember { mutableStateOf("Ghaziabad") }
    var weatherData by remember { mutableStateOf<Weather?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    
    LaunchedEffect(r) {
        isLoading = true
        
        delay(2000)
        weatherData = Weather(
            temperature = "${(20..30).random()}°C",
            humidity = "${(40..80).random()}%",
            windspeed = "${(5..20).random()} km/h"
        )
        isLoading = false
    }

    SideEffect {
        Log.d("WeatherApp", "UI recomposed for city: $r")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "City: $r", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)

        if (isLoading) {
            CircularProgressIndicator()
        } else if (weatherData != null) {
            Text(text = "Temperature: ${weatherData?.temperature}")
            Text(text = "Humidity: ${weatherData?.humidity}")
            Text(text = "Wind Speed: ${weatherData?.windspeed}")
        }

        Button(onClick = { r = if (r == "Ghaziabad") "anantapur" else "Rampur" },
            modifier = Modifier.padding(top = 16.dp)) {
            Text("Change City")
        }
    }
}
