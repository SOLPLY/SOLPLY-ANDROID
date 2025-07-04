package com.teamsolply.solply.maps.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.net.URLEncoder
import kotlin.math.PI
import kotlin.math.ln
import kotlin.math.tan

@SuppressLint("MissingPermission")
fun navigateToNaverMapDirections(
    context: Context,
    destName: String,
    destLongitude: Double,
    destLatitude: Double,
    destId: String,
    destType: String,
) {
    val permissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!permissionGranted) {
        Toast.makeText(context, "위치 권한이 필요합니다", Toast.LENGTH_SHORT).show()
        return
    }

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val (startXmerc, startYmerc) = latLngToMercator(
                    longitude = location.longitude,
                    latitude = location.latitude
                )
                val (destXmerc, destYmerc) = latLngToMercator(
                    longitude = destLongitude,
                    latitude = destLatitude
                )
                val encodedStartName = URLEncoder.encode("현재 위치", "UTF-8")
                val encodedDestName = URLEncoder.encode(destName, "UTF-8")

                val url = "https://map.naver.com/p/directions/" +
                        "$startXmerc,$startYmerc,$encodedStartName,0,USER_LOCATION/" +
                        "$destXmerc,$destYmerc,$encodedDestName,$destId,$destType/-/transit?c=11.00,0,0,0,dh"

                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            } else {
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }
        }
        .addOnFailureListener {
            Toast.makeText(context, "위치 요청 실패: ${it.message}", Toast.LENGTH_SHORT).show()
        }
}


fun latLngToMercator(longitude: Double, latitude: Double): Pair<Double, Double> {
    val x = longitude * 20037508.34 / 180
    val y = ln(tan((90 + latitude) * PI / 360)) * 20037508.34 / PI
    return Pair(x, y)
}