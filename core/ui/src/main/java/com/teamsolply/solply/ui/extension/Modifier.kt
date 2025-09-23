package com.teamsolply.solply.ui.extension

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.customClickable(
    rippleEnabled: Boolean = true,
    rippleColor: Color? = null,
    runIf: Boolean = true,
    singleClick: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)?
) = runIf(runIf) {
    composed {
        val multipleEventsCutter = remember { MultipleEventsCutter.get() }

        combinedClickable(
            onClick = {
                onClick?.let {
                    if (singleClick) {
                        multipleEventsCutter.processEvent {
                            it()
                        }
                    } else {
                        it()
                    }
                }
            },
            onLongClick = onLongClick,
            indication = if (rippleEnabled) {
                ripple(
                    color = rippleColor ?: Color.Unspecified
                )
            } else {
                null
            },
            interactionSource = remember { MutableInteractionSource() }
        )
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus(force = true)
        })
    }
}

fun Modifier.clearFocusOnTapOutside(
    focusManager: FocusManager,
    keyboard: SoftwareKeyboardController?
) = pointerInput(Unit) {
    awaitEachGesture {
        awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Final)
        focusManager.clearFocus(force = true)
        keyboard?.hide()
        waitForUpOrCancellation()
    }
}

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) this.modifier() else this
}

fun Modifier.shimmer(
    baseColor: Color,
    highlightColor: Color,
    animationDuration: Int = 1000
): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    drawWithContent {
        drawContent()
        val brush = Brush.linearGradient(
            colors = listOf(baseColor, highlightColor, baseColor),
            start = Offset(translateAnim.value - 300f, 0f),
            end = Offset(translateAnim.value, size.height)
        )
        drawRect(brush = brush, alpha = 0.6f)
    }
}
