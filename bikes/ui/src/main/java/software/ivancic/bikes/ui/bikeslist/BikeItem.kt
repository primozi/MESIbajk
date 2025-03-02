package software.ivancic.bikes.ui.bikeslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.bikes.ui.R

@Composable
internal fun BikeItem(
    bikeWithAvailabilityData: BikeWithAvailabilityData,
    onBikeClicked: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBikeClicked(bikeWithAvailabilityData.id)
            }
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp,
            )
    ) {
        Text(
            text = with(bikeWithAvailabilityData) { "$name (#$code)" },
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
        )

        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
            if (bikeWithAvailabilityData.isReserved) {
                Text(
                    text = stringResource(R.string.reserved),
                    color = MaterialTheme.colorScheme.error,
                )
            } else {
                Text(
                    text = stringResource(R.string.available),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

private class BooleanProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}


@Preview
@Composable
private fun BikeItemPreview(
    @PreviewParameter(BooleanProvider::class) isCurrentlyReserved: Boolean,
) {
    MaterialTheme {
        BikeItem(
            bikeWithAvailabilityData = BikeWithAvailabilityData(
                id = 1,
                name = "Glavko",
                code = "glavko_code",
                isCurrentlyReserved
            ),
            onBikeClicked = {}
        )
    }
}
