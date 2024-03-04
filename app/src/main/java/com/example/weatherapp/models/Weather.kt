package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Weather(
    val current: Current,
    val location: Location,
    val forecast: Forecast
)

data class Location(
    val name: String,
    val region: String,
    val country: String
)

data class Current (
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("wind_kph") val windSpeed: Float,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("feelslike_c") val feelsLike: Float,
    val condition: Condition
)

data class Condition (
    val text: String,
    val icon: String
)

//
// Forecast has a collection of ForeCastDay, and ForecastDay has a Day
//

data class Forecast (
    @SerializedName("forecastday") val forecastDays: List<ForecastDay>
)

data class ForecastDay (
    val date: String,
    val day: Day,
    @SerializedName("hour") val hours: List<Hour>
)

data class Day (
    @SerializedName("maxtemp_c") val maxTemp: Float,
    @SerializedName("mintemp_c") val minTemp: Float
)

data class Hour(
    @SerializedName("time_epoch") val epoch: Long,
    val time: String,
    @SerializedName("temp_c") val temperature: Float,
    @SerializedName("wind_kph") val windSpeed: Float,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("feelslike_c") val feelsLike: Float,
    val condition: Condition
)