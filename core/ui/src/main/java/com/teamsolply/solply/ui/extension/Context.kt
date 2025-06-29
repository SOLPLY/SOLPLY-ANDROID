package com.teamsolply.solply.ui.extension

import android.annotation.SuppressLint
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

@SuppressLint("MissingPermission")
fun Context.vibrate(milliseconds: Long = 50) {
    val vibrator = getSystemService(Vibrator::class.java)
    vibrator?.vibrate(
        VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE)
    )
}