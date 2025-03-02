package software.ivancic.bikes.ui.bikedetails

import software.ivancic.bikes.domain.model.BikeDetails
import software.ivancic.bikes.domain.usecases.GetBikeDetailsUseCase
import software.ivancic.bikes.ui.bikedetails.BikeDetailsViewModel.Action
import software.ivancic.bikes.ui.bikedetails.BikeDetailsViewModel.Effect
import software.ivancic.bikes.ui.bikedetails.BikeDetailsViewModel.State
import software.ivancic.core.ui.BaseViewModel

class BikeDetailsViewModel(
    private val getBikeDetails: GetBikeDetailsUseCase,
    private val bikeId: Int,
) : BaseViewModel<Action, Effect, State>(State()) {

    override suspend fun handleAction(action: Action) {
        when (action) {
            Action.LoadData -> {
                getBikeDetails(bikeId)
                    .onSuccess { data ->
                        updateState {
                            it.copy(
                                bikeName = data.name,
                                bikeCode = data.code,
                                lastReservation = data.latestReservationData,
                                nextReservation = data.nextReservationData,
                                kilometresRidden = data.kilometresRidden,
                                numberOfReservations = data.reservationsCount,
                            )
                        }
                    }
                    .onFailure {
                        // todo alert user
                        updateState { State() }
                    }
            }
        }
    }

    sealed interface Action {
        data object LoadData : Action
    }

    sealed interface Effect
    data class State(
        val bikeName: String = "",
        val bikeCode: String = "",
        val lastReservation: BikeDetails.BasicReservationData? = null,
        val nextReservation: BikeDetails.BasicReservationData? = null,
        val kilometresRidden: Int = 0,
        val numberOfReservations: BikeDetails.ReservationsCount = BikeDetails.ReservationsCount(),
    )
}
