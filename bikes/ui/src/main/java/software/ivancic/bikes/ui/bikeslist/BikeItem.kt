package software.ivancic.bikes.ui.bikeslist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.ui.R

@Composable
internal fun BikeItem(
    bike: Bike,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = bike.name,
            modifier = Modifier
                .weight(1f)
        )

        if (bike.isCurrentlyReserved) {
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

private class BooleanProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}


@Preview
@Composable
private fun BikeItemPreview(
    @PreviewParameter(BooleanProvider::class) isCurrentlyReserved: Boolean
) {
    MaterialTheme {
        BikeItem(
            bike = Bike(id = 1, name = "Glavko", isCurrentlyReserved),
        )
    }
}
