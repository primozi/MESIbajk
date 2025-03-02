package software.ivancic.bikes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import software.ivancic.bikes.ui.addreservation.AddReservationScreen
import software.ivancic.bikes.ui.bikeslist.BikesListScreen

fun NavGraphBuilder.bikesNavigationScreen(
    navController: NavController,
) {
    composable<BikesDestinations.BikesList> {
        BikesListScreen(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
        )
    }

    composable<BikesDestinations.AddBikeReservation> {
        AddReservationScreen(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
