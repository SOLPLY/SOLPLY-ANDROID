import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun ClickableAnnotatedText(
    modifier: Modifier = Modifier,
    originalText: String,
    originalTextStyle: TextStyle,
    targetText: String,
    spanStyle: SpanStyle,
    onClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append(
            originalText.replace(
                "\\n",
                "\n"
            )
        )
        val startIndex = originalText.indexOf(targetText)
        if (startIndex >= 0) {
            val endIndex = startIndex + targetText.length

            addStyle(style = spanStyle, start = startIndex, end = endIndex)
            addStringAnnotation(
                tag = "COPY",
                annotation = targetText,
                start = startIndex,
                end = endIndex
            )
        }
    }

    SelectionContainer {
        Text(
            text = annotatedText,
            modifier = modifier.clickable {
                onClick()
            },
            style = originalTextStyle
        )
    }
}

fun String.formatTextToNoticeSnackBar(): String {
    val processedText = if (this.length >= 9) {
        this.substring(0, 7) + ".."
    } else {
        this
    }
    return "'$processedText'"
}

fun String.formatTextToPlaceItem(): String = if (this.length > 15) {
    this.take(15) + "..."
} else {
    this
}

fun String.formatTextToPlaceItemTitle(): String = if (this.length > 8) {
    this.take(8) + "..."
} else {
    this
}
