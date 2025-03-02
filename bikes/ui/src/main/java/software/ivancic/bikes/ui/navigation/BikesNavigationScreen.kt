package software.ivancic.bikes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import software.ivancic.bikes.ui.addreservation.AddReservationScreen
import software.ivancic.bikes.ui.bikedetails.BikeDetailsScreen
import software.ivancic.bikes.ui.bikedetails.BikeDetailsViewModel
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

    composable<BikesDestinations.BikeDetails> {
        val details: BikesDestinations.BikeDetails = it.toRoute()
        val viewModel =
            koinViewModel<BikeDetailsViewModel>(parameters = { parametersOf(details.bikeId) })
        BikeDetailsScreen(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
