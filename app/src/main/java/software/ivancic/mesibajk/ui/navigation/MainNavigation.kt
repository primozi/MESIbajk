package software.ivancic.mesibajk.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import software.ivancic.bikes.ui.navigation.BikesDestinations
import software.ivancic.bikes.ui.navigation.bikesNavigationScreen

@Composable
fun MainNavigationScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.Bikes,
        modifier = modifier,
    ) {
        navigation<Destinations.Bikes>(startDestination = BikesDestinations.BikesList) {
            bikesNavigationScreen(
                navController = navController,
            )
        }
    }
}
