package software.ivancic.bikes.ui.navigation

import kotlinx.serialization.Serializable

internal sealed interface Destinations {
    @Serializable
    data object BikesList : Destinations
}
