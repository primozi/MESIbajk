package software.ivancic.bikes.ui.bikeslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.bikes.ui.R
import software.ivancic.bikes.ui.navigation.BikesDestinations

@Composable
internal fun BikesListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BikesListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BikesListScreenInternal(
        bikes = state.bikes,
        onAddReservationClicked = {
            viewModel.submitAction(BikesListViewModel.Action.AddReservationClicked)
        },
        onBikeClicked = {
            viewModel.submitAction(BikesListViewModel.Action.BikeClicked(it))
        },
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.submitAction(BikesListViewModel.Action.LoadData)
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest {
            when (it) {
                is BikesListViewModel.Effect.NavigateToAddReservation -> {
                    navController.navigate(BikesDestinations.AddBikeReservation)
                }

                is BikesListViewModel.Effect.NavigateToBikeDetails -> {
                    navController.navigate(BikesDestinations.BikeDetails(it.bikeId))
                }
            }
        }
    }
}

@Composable
private fun BikesListScreenInternal(
    bikes: List<BikeWithAvailabilityData>,
    onAddReservationClicked: () -> Unit,
    onBikeClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(bikes) {
                BikeItem(
                    it,
                    onBikeClicked = onBikeClicked
                )
            }
        }

        Button(
            onClick = onAddReservationClicked
        ) {
            Text(
                text = stringResource(R.string.add_reservation),
            )
        }
    }
}

@Preview
@Composable
private fun BikesListScreenInternalPreview() {
    MaterialTheme {
        BikesListScreenInternal(
            bikes = listOf(
                BikeWithAvailabilityData(id = 1, name = "Glavko", "code_Glavko", false),
                BikeWithAvailabilityData(id = 2, name = "Srečko", "code_Srečko", true),
                BikeWithAvailabilityData(id = 3, name = "Kihec", "code_Kihec", true),
                BikeWithAvailabilityData(id = 4, name = "Pikec", "code_Pikec", true),
                BikeWithAvailabilityData(id = 5, name = "Godrnjavko", "code_Godrnjavko", false),
                BikeWithAvailabilityData(id = 6, name = "Tepko", "code_Tepko", false),
                BikeWithAvailabilityData(id = 7, name = "Zaspanko", "code_Zaspanko", true),
            ),
            onAddReservationClicked = {},
            onBikeClicked = {},
        )
    }
}
