package com.jakubworoniecki.autentidemo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LaunchpadItem(
    val id: Int,
    val status: String,
    val location: LaunchpadLocation,
    val vehicles_launched: List<String>?,
    val attempted_launches: Int,
    val successful_launches: Int,
    val wikipedia: String?,
    val details: String?,
    val site_id: String,
    val site_name_long: String?
): Parcelable {
    @Parcelize
    data class LaunchpadLocation(
        val name: String,
        val region: String,
        val latitude: Double,
        val longitude: Double
    ) : Parcelable
}
