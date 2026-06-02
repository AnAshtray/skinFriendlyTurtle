package com.sunguard.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,weather_code,uv_index",
        @Query("hourly") hourly: String = "temperature_2m,uv_index",
        @Query("timezone") timezone: String = "auto",
    ): OpenMeteoResponse
}

data class OpenMeteoResponse(
    val current: OpenMeteoCurrent?,
)

data class OpenMeteoCurrent(
    @SerializedName("uv_index") val uvIndex: Double?,
)
