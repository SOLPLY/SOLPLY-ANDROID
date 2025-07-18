package com.teamsolply.solply.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.decode.GifDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun AdaptationImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    onLoadingStateChange: ((AsyncImagePainter.State) -> Unit)? = null
) {
    val context = LocalContext.current

    val isSvg = containsExtension(imageUrl, "svg")
    val isGif = containsExtension(imageUrl, "gif")

    val imageLoader = ImageLoader.Builder(context)
        .components {
            when {
                isSvg -> add(SvgDecoder.Factory())
                isGif -> add(GifDecoder.Factory())
            }
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()

    SubcomposeAsyncImage(
        model = request,
        imageLoader = imageLoader,
        contentScale = contentScale,
        contentDescription = contentDescription,
        modifier = modifier,
        onState = { state ->
            onLoadingStateChange?.invoke(state)
        }
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                onLoadingStateChange?.invoke(painter.state)
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
                onLoadingStateChange?.invoke(painter.state)
            }

            is AsyncImagePainter.State.Error -> {
                onLoadingStateChange?.invoke(painter.state)
            }

            AsyncImagePainter.State.Empty -> {}
        }
    }
}

private fun containsExtension(url: String, extension: String): Boolean {
    val regex = Regex("\\.$extension(\\?|$)", RegexOption.IGNORE_CASE)
    return regex.containsMatchIn(url)
}
