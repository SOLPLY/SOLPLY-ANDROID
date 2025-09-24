package com.teamsolply.solply.maps.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okio.BufferedSink

private fun ContentResolver.length(uri: Uri): Long {
    return query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)
        ?.use { c ->
            val idx = c.getColumnIndex(OpenableColumns.SIZE)
            if (idx >= 0 && c.moveToFirst()) c.getLong(idx) else -1L
        } ?: -1L
}

private class FixedLenUriBody(
    private val context: Context,
    private val uri: Uri,
    private val mime: String,
    private val length: Long
) : RequestBody() {
    override fun contentType() = mime.toMediaTypeOrNull()
    override fun contentLength() = length
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
    val cr = context.contentResolver
    val mime = cr.getType(uri) ?: "application/octet-stream"
    val len = cr.length(uri)
    require(len >= 0) { "Content length unknown; presigned PUT may reject chunked transfer" }

    val body = FixedLenUriBody(context, uri, mime, len)
    val req = Request.Builder()
        .url(presignedUrl)
        .put(body)
        .build()

    OkHttpClient().newCall(req).execute().use { resp ->
        if (!resp.isSuccessful) {
            val err = resp.body?.string()
            throw IllegalStateException("Upload failed: ${resp.code} body=$err")
        }
    }
}
