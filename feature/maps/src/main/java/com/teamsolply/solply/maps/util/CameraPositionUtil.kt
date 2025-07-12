package com.teamsolply.solply.maps.util

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.teamsolply.solply.maps.model.Place

fun calculateCameraPosition(places: List<Place>): CameraPosition {
    val latitudes = places.map { it.latitude.toDouble() }
    val longitudes = places.map { it.longitude.toDouble() }

    val minLat = latitudes.minOrNull() ?: 0.0
    val maxLat = latitudes.maxOrNull() ?: 0.0
    val minLng = longitudes.minOrNull() ?: 0.0
    val maxLng = longitudes.maxOrNull() ?: 0.0

    val centerLat = (minLat + maxLat) / 2
    val centerLng = (minLng + maxLng) / 2

    val latDiff = maxLat - minLat
    val lngDiff = maxLng - minLng
    val maxDiff = maxOf(latDiff, lngDiff)
    val zoomLevel = when {
        maxDiff > 0.1 -> 10.0
        maxDiff > 0.05 -> 12.0
        maxDiff > 0.01 -> 14.0
        else -> 16.0
    }

    return CameraPosition(
        LatLng(centerLat - 0.008, centerLng),
        zoomLevel,
        0.0,
        0.0
    )
}
