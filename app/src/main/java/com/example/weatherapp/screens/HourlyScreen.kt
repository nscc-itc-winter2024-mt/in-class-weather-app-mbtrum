package com.example.weatherapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.models.Hour
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherScreen(mainViewModel: MainViewModel) {
    // Get weather from ViewModel, and the UI will re-compose when ViewModel changes or weather data is loaded
    val weather by mainViewModel.weatherStateFlow.collectAsState()

    val forecastDays = weather?.forecast?.forecastDays

    // Want to display 24 hours after now
    val now = Date()
    var count = 0

    if(forecastDays != null) {
        LazyColumn {
            items(forecastDays) { forecastDay ->

                if(count < 24) {
                    Text(formatDate(forecastDay.date),
                        fontWeight = FontWeight.Bold
                    )
                }

                // Iterate through hours for forecastDay
                for (hour in forecastDay.hours) {
                    if(Date(hour.epoch * 1000).after(now)) {
                        if(count < 24) {
                            DisplayHour(hour)
                        }
                        count++
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayHour(hour: Hour) {
    // Condition icon
    val imgUrl = "https:" + hour.condition.icon
    imgUrl.replace("64x64","128x128")

    // Format the time
    val timeFormat = SimpleDateFormat("h a", Locale.CANADA)
    val strTime = timeFormat.format(Date(hour.epoch * 1000))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(15.dp)
    )
    {
        Text(strTime)

        AsyncImage(
            model = imgUrl,
            contentDescription = "Weather icon",
            modifier = Modifier.size(80.dp)
        )

        Text("${ hour.temperature.roundToInt() }°C")

        Spacer(modifier = Modifier.width(10.dp))

        Column () {
            Text(hour.condition.text)
            Text("Wind ${ hour.windDirection } ${ hour.windSpeed.roundToInt() }")
        }

    }
}

private fun formatDate(dateStr: String) : String {
    val date = java.sql.Date.valueOf(dateStr)
    val dateFormat: DateFormat = SimpleDateFormat("E, dd MMM", Locale.CANADA)
    return dateFormat.format(date)
}