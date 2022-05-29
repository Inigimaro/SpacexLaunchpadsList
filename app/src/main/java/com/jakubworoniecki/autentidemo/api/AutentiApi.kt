package com.jakubworoniecki.autentidemo.api

import com.jakubworoniecki.autentidemo.model.LaunchpadItem
import retrofit2.http.GET

interface AutentiApi {
    companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/launchpads/"
    }
    @GET(".")
    suspend fun getAllLaunchpads(): List<LaunchpadItem>
}