package software.ivancic.bikes.ui.navigation

import kotlinx.serialization.Serializable

sealed interface BikesDestinations {
    @Serializable
    data object BikesList : BikesDestinations

    @Serializable
    data object AddBikeReservation : BikesDestinations

    @Serializable
    data class BikeDetails(val bikeId: Int) : BikesDestinations
}
