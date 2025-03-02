package software.ivancic.bikes.ui.bikedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import software.ivancic.bikes.domain.model.BikeDetails
import software.ivancic.bikes.domain.model.Department
import software.ivancic.bikes.domain.model.Intent
import software.ivancic.bikes.ui.R
import software.ivancic.bikes.ui.toReadableString
import software.ivancic.core.ui.DateTimeFormattingUtil

@Composable
internal fun BikeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: BikeDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BikeDetailsScreenInternal(
        bikeName = state.bikeName,
        bikeCode = state.bikeCode,
        lastReservation = state.lastReservation,
        nextReservation = state.nextReservation,
        reservationsCount = state.numberOfReservations,
        modifier = modifier
            .padding(
                horizontal = 16.dp,
            ),
    )

    LaunchedEffect(Unit) {
        viewModel.submitAction(BikeDetailsViewModel.Action.LoadData)
    }
}

@Composable
private fun BikeDetailsScreenInternal(
    bikeName: String,
    bikeCode: String,
    lastReservation: BikeDetails.BasicReservationData?,
    nextReservation: BikeDetails.BasicReservationData?,
    reservationsCount: BikeDetails.ReservationsCount,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = bikeName,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "#$bikeCode",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        lastReservation?.let {
            ReservationLayout(
                label = stringResource(R.string.last_reservation),
                reservation = it,
            )
        }

        nextReservation?.let {
            ReservationLayout(
                label = stringResource(R.string.next_reservation),
                reservation = it,
            )
        }

        val tableSpacing = 4.dp
        Box(
            modifier = Modifier
                .background(Color.Black)
                .padding(tableSpacing)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(tableSpacing),
                modifier = Modifier
                    .background(Color.Black)
            ) {
                // header row
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(tableSpacing),
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color.Gray)
                        )

                        Intent.entries.forEach {
                            Text(
                                text = it.toReadableString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .background(Color.White)
                                    .weight(1f)
                                    .padding(
                                        horizontal = 4.dp,
                                        vertical = 2.dp,
                                    )
                            )
                        }
                    }
                }

                Department.entries.forEach { department ->
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(tableSpacing),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = department.toReadableString(),
                                textAlign = TextAlign.End,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .background(Color.White)
                                    .weight(1f)
                                    .padding(
                                        horizontal = 4.dp,
                                        vertical = 2.dp,
                                    )
                            )

                            Intent.entries.forEach { intent ->
                                Text(
                                    text = reservationsCount.getData(department, intent).toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .weight(1f)
                                        .padding(
                                            horizontal = 4.dp,
                                            vertical = 2.dp,
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationLayout(
    label: String,
    reservation: BikeDetails.BasicReservationData,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) { }
            Text(
                text = label,
            )
            Text(
                text = reservation.name,
            )
        }
        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.titleMedium) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = DateTimeFormattingUtil.formatDateTime(reservation.from))
                Text(text = " - ")
                Text(text = DateTimeFormattingUtil.formatDateTime(reservation.to))
            }
        }
    }
}

@Preview
@Composable
private fun BikeDetailsScreenInternalPreview() {
    MaterialTheme {
        BikeDetailsScreenInternal(
            bikeName = "Bike name",
            bikeCode = "Bike code",
            lastReservation = BikeDetails.BasicReservationData(
                name = "some employee",
                from = 0,
                to = 0,
            ),
            nextReservation = BikeDetails.BasicReservationData(
                name = "some employee",
                from = 0,
                to = 0,
            ),
            reservationsCount = BikeDetails.ReservationsCount(),
            modifier = Modifier
        )
    }
}
