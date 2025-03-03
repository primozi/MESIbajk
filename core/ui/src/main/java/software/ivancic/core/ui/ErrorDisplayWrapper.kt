package software.ivancic.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDisplayWrapper(
    errorText: String?,
    content: @Composable () -> Unit,
) {
    val errorColor = MaterialTheme.colorScheme.error
    val borderModifier by remember(errorText) {
        derivedStateOf {
            if (errorText.isNullOrBlank()) {
                Modifier
            } else {
                Modifier
                    .border(
                        width = 1.dp,
                        color = errorColor,
                    )
                    .padding(
                        all = 2.dp
                    )
            }
        }
    }
    Column(
        modifier = borderModifier
    ) {
        content()
        if (!errorText.isNullOrBlank()) {
            Text(
                text = errorText,
                color = errorColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

internal class ErrorParameterProvider : PreviewParameterProvider<String?> {
    override val values: Sequence<String?>
        get() = sequenceOf(null, "This is an error")
}

@Preview
@Composable
private fun ErrorDisplayWrapperPreview(
    @PreviewParameter(ErrorParameterProvider::class) errorText: String?,
) {
    MaterialTheme {
        ErrorDisplayWrapper(
            errorText = errorText,
            content = {
                Text(text = "This is some content")
            }
        )
    }
}
