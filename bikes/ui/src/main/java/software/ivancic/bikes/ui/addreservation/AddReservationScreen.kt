package software.ivancic.bikes.ui.addreservation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.domain.model.Department
import software.ivancic.bikes.domain.model.Intent
import software.ivancic.bikes.ui.R
import software.ivancic.bikes.ui.toReadableString
import software.ivancic.core.ui.DateTime
import software.ivancic.core.ui.DateTimeFormattingUtil
import software.ivancic.core.ui.DateTimePicker
import software.ivancic.core.ui.DropDownMenu
import software.ivancic.core.ui.theme.MESIbajkTheme
import kotlin.math.roundToInt


@Composable
fun AddReservationScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddReservationViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddReservationScreenInternal(
        borrowerName = state.borrowerName,
        selectedBike = state.selectedBike,
        availableBikes = state.availableBikes,
        onBikeSelected = {
            viewModel.submitAction(AddReservationViewModel.Action.BikeSelected(it))
        },
        onBorrowerNameChanged = {
            viewModel.submitAction(AddReservationViewModel.Action.BorrowerNameChanged(it))
        },
        availableDepartments = state.availableDepartments,
        selectedDepartment = state.selectedDepartment,
        onDepartmentSelected = {
            viewModel.submitAction(AddReservationViewModel.Action.DepartmentSelected(it))
        },
        from = state.from,
        onDateTimeFromClicked = {
            viewModel.submitAction(AddReservationViewModel.Action.DateTimeFromClicked)
        },
        to = state.to,
        onDateTimeToClicked = {
            viewModel.submitAction(AddReservationViewModel.Action.DateTimeToClicked)
        },
        approxDistanceInKm = state.approxDistanceInKm,
        onApproxDistanceInKmChanged = {
            viewModel.submitAction(AddReservationViewModel.Action.ApproxDistanceInKmChanged(it))
        },
        intent = state.intent,
        availableIntents = state.availableIntents,
        onIntentChanged = {
            viewModel.submitAction(AddReservationViewModel.Action.IntentChanged(it))
        },
        onAddClicked = {
            viewModel.submitAction(AddReservationViewModel.Action.AddClicked)
        },
        modifier = modifier
            .padding(
                horizontal = 16.dp,
            )
    )

    var showDateTimeFromDialog by remember { mutableStateOf(false) }
    var showDateTimeToDialog by remember { mutableStateOf(false) }

    if (showDateTimeFromDialog) {
        DateTimePicker(
            initialDateTime = state.from,
            onDismissClicked = {},
            onDateTimeSelected = {
                viewModel.submitAction(AddReservationViewModel.Action.DateTimeFromSelected(it))
                showDateTimeFromDialog = false
            }
        )
    }

    if (showDateTimeToDialog) {
        DateTimePicker(
            initialDateTime = state.to,
            onDismissClicked = {},
            onDateTimeSelected = {
                viewModel.submitAction(AddReservationViewModel.Action.DateTimeToSelected(it))
                showDateTimeToDialog = false
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                AddReservationViewModel.Effect.ShowDateTimePickerFrom -> {
                    showDateTimeFromDialog = true
                }

                AddReservationViewModel.Effect.ShowDateTimePickerTo -> {
                    showDateTimeToDialog = true
                }

                AddReservationViewModel.Effect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.submitAction(AddReservationViewModel.Action.LoadData)
    }
}

@Composable
private fun AddReservationScreenInternal(
    borrowerName: String,
    onBorrowerNameChanged: (String) -> Unit,
    selectedBike: Bike? = null,
    availableBikes: List<Bike>,
    onBikeSelected: (Bike) -> Unit,
    availableDepartments: List<Department>,
    selectedDepartment: Department?,
    onDepartmentSelected: (Department) -> Unit,
    from: Long,
    onDateTimeFromClicked: () -> Unit,
    to: Long,
    onDateTimeToClicked: () -> Unit,
    approxDistanceInKm: Int,
    onApproxDistanceInKmChanged: (Int) -> Unit,
    intent: Intent?,
    availableIntents: List<Intent>,
    onIntentChanged: (Intent) -> Unit,
    onAddClicked: () -> Unit,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        TextField(
            value = borrowerName,
            onValueChange = onBorrowerNameChanged,
            label = {
                Text(text = stringResource(R.string.users_name))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        DropDownMenu(
            selectedItem = selectedBike,
            availableItems = availableBikes,
            onItemSelected = onBikeSelected,
            selectedItemComposable = {
                Text(
                    text = it?.name ?: stringResource(R.string.select_bike),
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            listItemComposable = {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        DropDownMenu(
            selectedItem = selectedDepartment,
            availableItems = availableDepartments,
            onItemSelected = onDepartmentSelected,
            selectedItemComposable = {
                Text(
                    text = it?.toReadableString() ?: stringResource(R.string.select_department),
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            listItemComposable = {
                Text(
                    text = it.toReadableString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DateTime(
                label = stringResource(R.string.from),
                date = DateTimeFormattingUtil.formatDate(from),
                time = DateTimeFormattingUtil.formatTime(from),
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = onDateTimeFromClicked
                    )
            )

            DateTime(
                label = stringResource(R.string.to),
                date = DateTimeFormattingUtil.formatDate(to),
                time = DateTimeFormattingUtil.formatTime(to),
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        onClick = onDateTimeToClicked
                    )
            )
        }

        val maxValue = 50
        Slider(
            value = approxDistanceInKm.toFloat(),
            onValueChange = {
                onApproxDistanceInKmChanged(it.roundToInt())
            },
            valueRange = 0f..maxValue.toFloat(),
            steps = maxValue,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = "$approxDistanceInKm km",
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        DropDownMenu(
            selectedItem = intent,
            availableItems = availableIntents,
            onItemSelected = onIntentChanged,
            selectedItemComposable = {
                Text(
                    text = it?.toReadableString() ?: stringResource(R.string.select_intent),
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            listItemComposable = {
                Text(
                    text = it.toReadableString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onAddClicked,
        ) {
            Text(text = stringResource(R.string.add))
        }
    }
}

@Preview
@Composable
private fun AddReservationScreenInternalPreview() {
    MESIbajkTheme {
        AddReservationScreenInternal(
            modifier = Modifier,
            onDateTimeFromClicked = {},
            onDateTimeToClicked = {},
            borrowerName = "Ivan",
            onBorrowerNameChanged = {},
            selectedDepartment = null,
            onDepartmentSelected = {},
            availableDepartments = Department.entries,
            approxDistanceInKm = 10,
            onApproxDistanceInKmChanged = {},
            intent = null,
            availableIntents = Intent.entries,
            onIntentChanged = {},
            onAddClicked = {},
            from = 0L,
            to = 0L,
            availableBikes = emptyList(),
            onBikeSelected = {},
            selectedBike = null
        )
    }
}
