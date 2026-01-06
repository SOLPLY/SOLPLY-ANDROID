package com.teamsolply.solply.designsystem.component.card

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage

@Composable
fun RegisterPlaceImage(
    selectedUris: List<Uri>,
    onAddClick: () -> Unit,
    resetSelectedUris: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        repeat(3) { index ->
            when {
                index < selectedUris.size -> {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SolplyTheme.colors.gray100)
                            .customClickable(rippleEnabled = false) {
                                resetSelectedUris(index)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        AdaptationImage(
                            imageUrl = selectedUris[index].toString(),
                            contentDescription = "selected image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                index == selectedUris.size && selectedUris.size < 3 -> {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(
                                color = SolplyTheme.colors.gray200,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .customClickable(rippleEnabled = false) {
                                onAddClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cross),
                            contentDescription = "add_picture",
                            tint = SolplyTheme.colors.gray400
                        )
                    }
                }

                else -> {
                    val borderColor = SolplyTheme.colors.gray400
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val corner = 16.dp.toPx()
                                val dash = floatArrayOf(4.dp.toPx(), 4.dp.toPx())
                                val inset = strokeWidth / 2f
                                drawRoundRect(
                                    color = borderColor,
                                    topLeft = Offset(inset, inset),
                                    size = Size(
                                        size.width - strokeWidth,
                                        size.height - strokeWidth
                                    ),
                                    cornerRadius = CornerRadius(corner, corner),
                                    style = Stroke(
                                        width = strokeWidth,
                                        pathEffect = PathEffect.dashPathEffect(dash)
                                    )
                                )
                            }
                    )
                }
            }
        }
    }
}

fun ContentResolver.getFileName(uri: Uri): String {
    query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
        val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (idx >= 0 && cursor.moveToFirst()) {
            val name = cursor.getString(idx)
            if (!name.isNullOrBlank()) return name
        }
    }
    uri.path?.substringAfterLast('/')?.takeIf { it.isNotBlank() && it.contains('.') }
        ?.let { return it }
    val mime = getType(uri)
    val ext = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime) ?: "jpg"
    return "image_${System.currentTimeMillis()}.$ext"
}
