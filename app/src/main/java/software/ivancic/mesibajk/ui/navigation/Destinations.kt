package software.ivancic.mesibajk.ui.navigation

import kotlinx.serialization.Serializable

internal sealed interface Destinations {
    @Serializable
    data object Bikes : Destinations
}
