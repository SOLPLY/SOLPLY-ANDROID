package com.teamsolply.solply.maps.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
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
    destX: Double,
    destY: Double,
    destId: String,
    destType: String = "PLACE_POI"
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
                val lat = location.latitude
                val lng = location.longitude

                val startX = lng * 20037508.34 / 180
                val startY = ln(tan((90 + lat) * PI / 360)) * 20037508.34 / PI

                val destXmerc = destX * 20037508.34 / 180
                val destYmerc = ln(tan((90 + destY) * PI / 360)) * 20037508.34 / PI

                val encodedStartName = URLEncoder.encode("현재 위치", "UTF-8")
                val encodedDestName = URLEncoder.encode(destName, "UTF-8")

                val url = "https://map.naver.com/p/directions/" +
                    "$startX,$startY,$encodedStartName,0,USER_LOCATION/" +
                    "$destXmerc,$destYmerc,$encodedDestName,$destId,$destType/-/transit?c=11.00,0,0,0,dh"

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
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
