package software.ivancic.bikes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import software.ivancic.bikes.ui.bikeslist.BikesListScreen

@Composable
internal fun BikesNavigationScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.BikesList,
        modifier = modifier,
    ) {
        composable<Destinations.BikesList> {
            BikesListScreen()
        }
    }
}
