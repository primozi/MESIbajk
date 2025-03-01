package software.ivancic.bikes.ui.bikeslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.ui.R

@Composable
internal fun BikesListScreen(
    viewModel: BikesListViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BikesListScreenInternal(
        bikes = state.bikes,
        onAddReservationClicked = {
            viewModel.submitAction(BikesListViewModel.Action.AddReservationClicked)
        },
        modifier = Modifier
            .fillMaxSize()
    )

    LaunchedEffect(Unit) {
        viewModel.submitAction(BikesListViewModel.Action.LoadData)
    }
}

@Composable
fun BikesListScreenInternal(
    bikes: List<Bike>,
    onAddReservationClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(bikes) {
                BikeItem(it)
            }
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(
                onClick = onAddReservationClicked
            ) {
                Text(
                    text = stringResource(R.string.add_reservation),
                )
            }
        }
    }
}

@Preview
@Composable
private fun BikesListScreenInternalPreview() {
    MaterialTheme {
        BikesListScreenInternal(
            bikes = listOf(
                Bike(id = 1, name = "Glavko", false),
                Bike(id = 2, name = "Srečko", true),
                Bike(id = 3, name = "Kihec", true),
                Bike(id = 4, name = "Pikec", true),
                Bike(id = 5, name = "Godrnjavko", false),
                Bike(id = 6, name = "Tepko", false),
                Bike(id = 7, name = "Zaspanko", true),
            ),
            onAddReservationClicked = {}
        )
    }
}
