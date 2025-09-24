package com.teamsolply.solply.maps.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.BufferedSink

private fun Context.mimeOf(uri: Uri): String =
    contentResolver.getType(uri) ?: "application/octet-stream"

private class UriStreamBody(
    private val context: Context,
    private val uri: Uri,
    private val mime: String
) : RequestBody() {
    override fun contentType() = mime.toMediaTypeOrNull()
    override fun writeTo(sink: BufferedSink) {
        context.contentResolver.openInputStream(uri)?.use { input ->
            input.copyTo(sink.outputStream())
        }
    }
}

fun uploadToPresignedUrl(
    context: Context,
    uri: Uri,
    presignedUrl: String
) {
    val client = OkHttpClient()
    val mime = context.mimeOf(uri)
    val body = UriStreamBody(context, uri, mime)

    val req = Request.Builder()
        .url(presignedUrl)
        .put(body)
        .header("Content-Type", mime) // presigned 생성 시 포함된 Content-Type과 일치 필요
        .build()

    client.newCall(req).execute().use { resp ->
        if (!resp.isSuccessful) error("Upload failed: ${resp.code}")
    }
}

data class PresignedUrlInfo(
    val originalFileName: String,
    val tempFileKey: String,
    val presignedUrl: String,
    val expirationSeconds: Long
)