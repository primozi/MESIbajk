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
                        updateState { it.copy(bikeWithAvailabilityData = bikes) }
                    }
                    .onFailure { t ->
                        // todo alert user
                        updateState { it.copy(bikeWithAvailabilityData = emptyList()) }
                    }
            }

            Action.AddReservationClicked -> {
                emitEffect(Effect.NavigateToAddReservation)
            }
        }
    }

    sealed interface Action {
        data object LoadData : Action
        data object AddReservationClicked : Action
    }

    sealed interface Effect {
        data object NavigateToAddReservation : Effect
    }

    data class State(
        val bikeWithAvailabilityData: List<BikeWithAvailabilityData> = emptyList(),
    )
}
