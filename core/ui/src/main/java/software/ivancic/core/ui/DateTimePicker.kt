@file:OptIn(ExperimentalMaterial3Api::class)

package software.ivancic.core.ui

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@Composable
fun DateTimePicker(
    initialDateTime: Long,
    onDismissClicked: () -> Unit,
    onDateTimeSelected: (Long) -> Unit,
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val minute = Calendar.getInstance().get(Calendar.MINUTE)

    var showPicker: Pickers by remember { mutableStateOf(Pickers.DATE) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateTime)
    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true
    )
    var selectedDateMillis by remember { mutableLongStateOf(0L) }

    when (showPicker) {
        Pickers.DATE -> {
            DatePickerDialog(
                onDismissRequest = onDismissClicked,
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDateMillis = datePickerState.selectedDateMillis!!
                            showPicker = Pickers.TIME
                        }
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onDismissClicked,
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                },
                content = {
                    DatePicker(state = datePickerState)
                }
            )
        }

        Pickers.TIME -> {
            DatePickerDialog(
                onDismissRequest = onDismissClicked,
                confirmButton = {
                    TextButton(
                        onClick = {
                            val timeMillis =
                                (timePickerState.hour * 3600 + timePickerState.minute * 60) * 1000L
                            onDateTimeSelected(selectedDateMillis + timeMillis)
                        }
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = onDismissClicked,
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                },
                content = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}

private enum class Pickers {
    DATE, TIME
}

@Preview
@Composable
private fun DateTimePickerPreview() {
    MaterialTheme {
        DateTimePicker(
            initialDateTime = 0L,
            onDismissClicked = {},
            onDateTimeSelected = {}
        )
    }
}
