package software.ivancic.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import software.ivancic.core.ui.theme.MESIbajkTheme

@Composable
fun DateTime(
    label: String,
    date: String,
    time: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = date,
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = time,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
private fun DateSelectorPreview() {
    MESIbajkTheme {
        DateTime(
            label = "Datum",
            date = "12.12.2023",
            time = "12:12",
        )
    }
}
