package software.ivancic.bikes.ui.addreservation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import software.ivancic.bikes.domain.model.Bike
import software.ivancic.bikes.domain.model.Department
import software.ivancic.bikes.domain.model.Intent
import software.ivancic.bikes.domain.model.ReservationData
import software.ivancic.bikes.domain.usecases.GetListOfBikesUseCase
import software.ivancic.bikes.domain.usecases.SaveReservationDataUseCase
import software.ivancic.bikes.ui.addreservation.AddReservationViewModel.Action
import software.ivancic.bikes.ui.addreservation.AddReservationViewModel.Effect
import software.ivancic.bikes.ui.addreservation.AddReservationViewModel.State
import software.ivancic.core.ui.BaseViewModel
import java.util.Date

@KoinViewModel
class AddReservationViewModel(
    private val saveReservationData: SaveReservationDataUseCase,
    private val getBikes: GetListOfBikesUseCase,
) : BaseViewModel<Action, Effect, State>(State()) {

    override suspend fun handleAction(action: Action) {
        when (action) {
            Action.LoadData -> {
                getBikes(Unit)
                    .onSuccess { bikes ->
                        updateState { it.copy(availableBikes = bikes) }
                    }
                    .onFailure {
                        // todo alert user
                    }
            }

            Action.AddClicked -> {
                viewModelScope.launch {
                    val currentState = state.value
                    saveReservationData(
                        ReservationData(
                            bike = currentState.selectedBike!!,
                            start = currentState.from,
                            end = currentState.to,
                            usersName = currentState.borrowerName,
                            intent = currentState.intent!!,
                            department = currentState.selectedDepartment!!,
                            kilometres = currentState.approxDistanceInKm,
                        )
                    ).onSuccess {
                        emitEffect(Effect.NavigateBack)
                    }.onFailure {
                        // todo alert user
                    }
                }
            }

            is Action.ApproxDistanceInKmChanged -> {
                updateState { it.copy(approxDistanceInKm = action.newKm) }
            }

            is Action.BorrowerNameChanged -> {
                updateState { it.copy(borrowerName = action.newName) }
            }

            Action.DateTimeFromClicked -> {
                emitEffect(Effect.ShowDateTimePickerFrom)
            }

            Action.DateTimeToClicked -> {
                emitEffect(Effect.ShowDateTimePickerTo)
            }

            is Action.DepartmentSelected -> {
                updateState { it.copy(selectedDepartment = action.newDepartment) }
            }

            is Action.IntentChanged -> {
                updateState { it.copy(intent = action.newIntent) }
            }

            is Action.BikeSelected -> {
                updateState { it.copy(selectedBike = action.newBike) }
            }

            is Action.DateTimeFromSelected -> {
                updateState { it.copy(from = action.from) }
            }

            is Action.DateTimeToSelected -> {
                updateState { it.copy(to = action.to) }
            }
        }
    }

    sealed interface Action {
        data class BorrowerNameChanged(val newName: String) : Action
        data class BikeSelected(val newBike: Bike) : Action
        data class DepartmentSelected(val newDepartment: Department) : Action
        data class ApproxDistanceInKmChanged(val newKm: Int) : Action
        data class IntentChanged(val newIntent: Intent) : Action
        data class DateTimeFromSelected(val from: Long) : Action
        data class DateTimeToSelected(val to: Long) : Action

        data object DateTimeFromClicked : Action
        data object DateTimeToClicked : Action
        data object AddClicked : Action
        data object LoadData : Action
    }

    sealed interface Effect {
        data object ShowDateTimePickerFrom : Effect
        data object ShowDateTimePickerTo : Effect
        data object NavigateBack : Effect
    }

    data class State(
        val borrowerName: String = "",
        val selectedBike: Bike? = null,
        val availableBikes: List<Bike> = emptyList(),
        val selectedDepartment: Department? = null,
        val availableDepartments: List<Department> = Department.entries,
        val availableIntents: List<Intent> = Intent.entries,
        val from: Long = Date().time,
        val to: Long = Date().time,
        val approxDistanceInKm: Int = 0,
        val intent: Intent? = null,
    )
}
