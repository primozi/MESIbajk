package software.ivancic.bikes.ui.bikeslist

import org.koin.android.annotation.KoinViewModel
import software.ivancic.bikes.domain.model.BikeWithAvailabilityData
import software.ivancic.bikes.domain.usecases.GetListOfBikesWithAvailabilityDataUseCase
import software.ivancic.bikes.ui.bikeslist.BikesListViewModel.Action
import software.ivancic.bikes.ui.bikeslist.BikesListViewModel.Effect
import software.ivancic.bikes.ui.bikeslist.BikesListViewModel.State
import software.ivancic.core.ui.BaseViewModel

@KoinViewModel
class BikesListViewModel(
    private val getBikesWithAvailabilityData: GetListOfBikesWithAvailabilityDataUseCase,
) : BaseViewModel<Action, Effect, State>(State()) {

    override suspend fun handleAction(action: Action) {
        when (action) {
            Action.LoadData -> {
                getBikesWithAvailabilityData(Unit)
                    .onSuccess { bikes ->
                        updateState { it.copy(bikes = bikes) }
                    }
                    .onFailure { t ->
                        // todo alert user
                        updateState { it.copy(bikes = emptyList()) }
                    }
            }

            Action.AddReservationClicked -> {
                emitEffect(Effect.NavigateToAddReservation)
            }

            is Action.BikeClicked -> {
                emitEffect(Effect.NavigateToBikeDetails(action.id))
            }
        }
    }

    sealed interface Action {
        data class BikeClicked(val id: Int) : Action

        data object LoadData : Action
        data object AddReservationClicked : Action
    }

    sealed interface Effect {
        data object NavigateToAddReservation : Effect
        data class NavigateToBikeDetails(val bikeId: Int) : Effect
    }

    data class State(
        val bikes: List<BikeWithAvailabilityData> = emptyList(),
    )
}
