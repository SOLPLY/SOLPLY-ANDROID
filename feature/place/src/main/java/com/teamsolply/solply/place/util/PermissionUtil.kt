package com.teamsolply.solply.place.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Process
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.teamsolply.solply.designsystem.theme.SolplyTheme

@Composable
fun LocationPermissionRequest() {
    val context = LocalContext.current
    var hasLocationPermission by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
        if (!isGranted) {
            showPermissionDialog = true
        }
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                hasLocationPermission = true
                return@LaunchedEffect
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("위치 권한 필요") },
            text = { Text("이 기능을 사용하려면 위치 권한이 필요합니다. 설정에서 권한을 허용해주세요.") },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        showPermissionDialog = false
                        val intent =
                            android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .apply {
                                    data = android.net.Uri.fromParts(
                                        "package",
                                        context.packageName,
                                        null
                                    )
                                }
                        context.startActivity(intent)
                    }
                ) {
                    Text("설정으로 이동")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(
                    onClick = { Process.killProcess(Process.myPid()) }
                ) {
                    Text("취소")
                }
            },
            containerColor = SolplyTheme.colors.white
        )
    }
}
